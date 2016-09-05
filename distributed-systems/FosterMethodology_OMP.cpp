/*
 *  FosterMethodology_OMP.cpp
 *
 *  Created on: Dec 12, 2014
 *      Author: root
 */

#include <bits/stdc++.h>
#include <omp.h>
#include <ctime>

using namespace std;

int main() {
	clock_t begin = clock();
	//declaring variables
	//int data_count, bin_count, max_meas, min_meas; //input
	int input[4]; //input[0] = data_count, input[1] = min_meas, input[2] = max_meas, input[3] = bin_count;
	int i;
	double bin_width;

	//opening the input file to read the data
	fstream inFile("huge_arraydata.txt", ios::in);

	//read the size of data
	inFile >> input[0];
	double data[input[0]]; //declaring the data array

	//reading data
	for (int i = 0; i < input[0]; i++)
		inFile >> data[i];

	inFile >> input[1] >> input[2]; //reading minimum measure & max measure
	inFile >> input[3]; //reading number of bins

	//computing the bin_width
	bin_width = (input[2] - input[1]) * 1.0 / input[3];

	//initializing global and local array to store bin lengths
	int bins[input[3]];
	int local_bins[input[3]];

	//initializing bins to 0's
	for (int i = 0; i < input[3]; i++)
		bins[i] = 0;

#pragma omp parallel shared(bins, data, bin_width) private(local_bins, i) num_threads(4)
	{ //starting the parallel section
		for (i = 0; i < input[3]; i++)
			local_bins[i] = 0; //initializing the local_bins for each thread


#pragma omp for schedule(static) //distributing the data over all the threads
		for (i = 0; i < input[0]; i++)
			local_bins[(int) ((data[i] - input[1]) * 1.0 / bin_width)]++;

#pragma omp critical //reducing all the local_bin's into the global bins
		for (i = 0; i < input[3]; i++)
			bins[i] += local_bins[i];

#pragma omp barrier //barrier to make sure all threads have done their work

#pragma omp master //master threads now displaying the results
		for (i = 0; i < input[3]; i++) {
			double start = input[1] + (i * bin_width);
			double end = input[1] + ((i + 1) * bin_width);
			printf("bin no.%d takes range (%.02f <-> %.02f) and has %2d value(s).\n", i + 1, start, end, bins[i]);
		} //end of for-loop
	} //end of parallel-section

	clock_t end = clock();
	double execution_time = double(end - begin) / CLOCKS_PER_SEC;
	printf("excution time: %0.2f second.\n", execution_time);
} //end of main-method
