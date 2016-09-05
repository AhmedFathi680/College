/*
 * FosterMethodology_MPI.cpp
 *
 *  Created on: Dec 13, 2014
 *      Author: root
 */

#include <bits/stdc++.h>
#include "mpi.h"
#include <ctime>

using namespace std;

int main(int argc, char **argv) {

	MPI_Init(&argc, &argv);
	clock_t begin = clock();
	//declaring variables
	int input[4]; //input[0] = data_count, input[1] = min_meas, input[2] = max_meas, input[3] = bin_count;
	double bin_width;

	//MPI variables
	int size, myrank;
	MPI_Status status;
	int partial_size;

	MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	if (myrank == 0) { //master node

		//opening the input file to read the data
		fstream inFile("huge_arraydata.txt", ios::in);

		//read the size of data
		inFile >> input[0]; //data_count
		double data[input[0]]; //declaring the data array

		//reading data
		for (int i = 0; i < input[0]; i++)
			inFile >> data[i];

		inFile >> input[1] >> input[2]; //min_meas, max_meas
		inFile >> input[3]; //bin_count

		//computing the bin_width
		bin_width = (input[2] - input[1]) * 1.0 / input[3];

		//initializing global and local array to store bin lengths
		int bins[input[3]];
		int local_bins[input[3]];

		partial_size = input[0] / size; //size should be proceed by each node

		//sending the partial data for each non-master node
		for (int i = 1; i < size; i++) {
			MPI_Send(input, 4, MPI_INT, i, 25, MPI_COMM_WORLD); //sending all input size
			MPI_Send(data + (i * partial_size), partial_size, MPI_DOUBLE, i, 25, MPI_COMM_WORLD); //distributing the elements
		}

		//initializing bins & local_bins to 0's
		for (int i = 0; i < input[3]; i++) {
			bins[i] = 0;
			local_bins[i] = 0;
		}

		//computing local bin for master node
		for (int i = 0; i < partial_size; i++)
			bins[(int) ((data[i] - input[1]) * 1.0 / bin_width)]++;

		//receiving local_bins from each non-master node
		for (int i = 1; i < size; i++) {
			MPI_Recv(local_bins, input[3], MPI_INT, i, 25, MPI_COMM_WORLD, &status);
			for (int j = 0; j < input[3]; j++) {
				bins[j] += local_bins[j];
			}
		}

		//printing results
		for (int i = 0; i < input[3]; i++) {
			double start = input[1] + (i * bin_width);
			double end = input[1] + ((i + 1) * bin_width);
			printf("bin no.%d takes range (%.02f <-> %.02f) and has %2d value(s).\n", i + 1, start, end, bins[i]);
		}

		clock_t end = clock();
		double execution_time = double(end - begin) / CLOCKS_PER_SEC;
		printf("excution time: %0.2f second.\n", execution_time);
	} //end of master node block
	else {
		MPI_Recv(input, 4, MPI_INT, 0, 25, MPI_COMM_WORLD, &status);
		partial_size = input[0] / size;
		double local_data[partial_size];
		int local_bins[input[3]];
		for (int i = 0; i < input[3]; i++)
			local_bins[i] = 0;

		//computing the bin_width
		bin_width = (input[2] - input[1]) * 1.0 / input[3];

		MPI_Recv(local_data, partial_size, MPI_DOUBLE, 0, 25, MPI_COMM_WORLD, &status);
		for (int i = 0; i < partial_size; i++)
			local_bins[(int) ((local_data[i] - input[1]) * 1.0 / bin_width)]++;

		MPI_Send(local_bins, input[3], MPI_INT, 0, 25, MPI_COMM_WORLD);
	} //end of non-master node block
	MPI_Finalize();

} //end of main-method
