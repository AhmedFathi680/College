/*
 * FosterMethodology_Hybrid.cpp
 *
 *  Created on: Dec 19, 2014
 *      Author: root
 */

#include <bits/stdc++.h>
#include <mpi.h>
#include <omp.h>
#include <ctime>

using namespace std;

int main(int argc, char **argv) {
	MPI_Init(&argc, &argv); //initializing the MPI
	clock_t begin = clock(); //computing the start-up-time for the program

	//declaring variables
	int input[4]; //input[0] = data_count, input[1] = min_meas, input[2] = max_meas, input[3] = bin_count;
	double bin_width;

	//MPI variables
	int size, myrank;
	MPI_Status status;
	int partial_size;

	//OMP variables
	int i = 0;
	MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	if (myrank == 0) { //master node block
		fstream inFile("huge_arraydata.txt", ios::in); //opening the input file to read the data

		inFile >> input[0]; //data_count
		double data[input[0]]; //declaring the data array

		for (i = 0; i < input[0]; i++)
			inFile >> data[i]; //reading data

		inFile >> input[1] >> input[2]; //min_meas, max_meas
		inFile >> input[3]; //bin_count

		bin_width = (input[2] - input[1]) * 1.0 / input[3]; //computing the bin_width

		int bins[input[3]]; //global array for final lengths
		int local_bins[input[3]]; //local array for each node
		int sub_local_bins[input[3]]; //sub_local array for each thread in each node

		partial_size = input[0] / size; //size should be proceed by each node

		for (i = 1; i < size; i++) { //sending the partial data to each non-master node
			MPI_Send(input, 4, MPI_INT, i, 25, MPI_COMM_WORLD); //sending all input
			MPI_Send(data + (i * partial_size), partial_size, MPI_DOUBLE, i, 25, MPI_COMM_WORLD); //distributing data
		}

		for (i = 0; i < input[3]; i++) //initializing bins, local_bins & sub_local_bins
			bins[i] = 0, local_bins[i] = 0, sub_local_bins[i] = 0;

		double local_data[partial_size]; //declaring local_data
		for (i = 0; i < partial_size; i++)
			local_data[i] = data[i]; //initializing local_data

#pragma omp parallel shared(bins, local_data, bin_width) private(sub_local_bins, i) num_threads(4)
		{ //starting parallel section in the master node
			for (i = 0; i < input[3]; i++)
				sub_local_bins[i] = 0; //initializing the local_bins for each thread in the master node

#pragma omp for schedule(static) //distributing the data over all the threads
			for (i = 0; i < partial_size; i++) { //partial_size = 5
				sub_local_bins[(int) ((local_data[i] - input[1]) * 1.0 / bin_width)]++;
			}

#pragma omp critical //reducing all the sub_local_bin's
			for (i = 0; i < input[3]; i++)
				bins[i] += sub_local_bins[i];
		} //end of parallel-section for master node

		for (i = 1; i < size; i++) { //receiving local_bins from each non-master node
			MPI_Recv(local_bins, input[3], MPI_INT, i, 25, MPI_COMM_WORLD, &status); //receiving data
			for (int j = 0; j < input[3]; j++) {
				bins[j] += local_bins[j]; //reducing the local_bins into the global bin
			}
		} //end of for-loop

		for (i = 0; i < input[3]; i++) { //printing final results (global bin)
			double start = input[1] + (i * bin_width);
			double end = input[1] + ((i + 1) * bin_width);
			printf("bin no.%d takes range (%.02f <-> %.02f) and has %2d value(s).\n", i + 1, start, end, bins[i]);
		} //end printing results

		clock_t end = clock(); //computing the end-time for the program
		double execution_time = double(end - begin) / CLOCKS_PER_SEC; //computing the execution time
		printf("excution time: %0.2f second.\n", execution_time);
	} //end of the master-node block
	else { //non-master nodes block
		MPI_Recv(input, 4, MPI_INT, 0, 25, MPI_COMM_WORLD, &status); //receiving the input from the master node

		partial_size = input[0] / size; //size should be proceed by each node

		double local_data[partial_size]; //local_data for each non-master node
		int local_bins[input[3]]; //local_bins for each non-master node
		int sub_local_bins[input[3]]; //sub_local_bins for each thread in the non_master node

		for (i = 0; i < input[3]; i++) //initializing the local_bins & sub_local_bins
			local_bins[i] = 0, sub_local_bins[i] = 0;

		bin_width = (input[2] - input[1]) * 1.0 / input[3]; //computing the bin_width

		//each non-master node receiving its local data from the master node
		MPI_Recv(local_data, partial_size, MPI_DOUBLE, 0, 25, MPI_COMM_WORLD, &status);

#pragma omp parallel shared(local_bins, local_data, bin_width) private(sub_local_bins, i) num_threads(4)
		{ //starting the parallel section for each non-master node
			for (i = 0; i < input[3]; i++)
				sub_local_bins[i] = 0; //initializing the local_bins for each thread in the each non-master node

#pragma omp for schedule(static) //distributing the data over all threads
			for (i = 0; i < partial_size; i++)
				sub_local_bins[(int) ((local_data[i] - input[1]) * 1.0 / bin_width)]++;

#pragma omp critical //reducing sub_local_bin's of the threads into local_bins
			for (i = 0; i < input[3]; i++)
				local_bins[i] += sub_local_bins[i];
		} //end of parallel-section

		//send local_bins to the master node
		MPI_Send(local_bins, input[3], MPI_INT, 0, 25, MPI_COMM_WORLD);
	} //end of non-master nodes block

	MPI_Finalize(); //finalizing the MPI

} //end of main-method
