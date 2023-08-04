import time
import random
import math


class union_find:
    def __init__(self, size, print=False):
        self.print = print
        self.id = []
        for i in range(size):
            self.id.append(i)
        self.sz = []
        for i in range(size):
            self.sz.append(1)


    def root(self, i):
        while (i != self.id[i]):
            self.id[i] = self.id[self.id[i]]
            i = self.id[i]
        return i


    def connected(self, i, j):
        return self.root(i) == self.root(j)


    def union(self, p, q):
        i = self.root(p)
        j = self.root(q)

        '''
        if the elements are already connected:
            pass
        '''

        if self.sz[i] < self.sz[j]:
            self.id[i] = j
            self.sz[j] += self.sz[i]
        else:
            self.id[j] = i
            self.sz[i] += self.sz[j]

        if self.print:
            print(f"connected {p} with root {i} to {q} with root {j}")


class Percolation():
    def __init__(self, size, print=False):
        if size <= 0:
            raise ValueError
        self.print = print
        self.size = size
        self.num_of_open_sites = 0
        self.grid = []
        for i in range(size):
            row = []
            for i in range(size):
                row.append(0)
            self.grid.append(row)
        
        self.uf = union_find(size**2+2)
        for i in range(self.size):
            self.uf.union(i, self.uf.id[-1])
        for i in range(self.size**2 - self.size, self.size**2):
            self.uf.union(i, self.uf.id[-2])


    def visualise(self):
        print()
        for row in self.grid:
            for i in row:
                print(f"{i} ", end='')
            print()
        print()


    def convert_indexes(self, row, col) -> int:
        return self.size*row + col


    def open(self, row, col):
        if row < 0 or col < 0 or row >= self.size or col >= self.size:
            raise ValueError
        self.grid[row][col] = 1
        if self.print:
            print(f"Opened [{row}, {col}]")
        self.num_of_open_sites += 1

        # check on the left
        if col != 0:
            if self.grid[row][col-1] == 1:
                i = self.convert_indexes(row, col)
                j = self.convert_indexes(row, col-1)
                self.uf.union(i, j)

        # check on the right
        if col != self.size - 1:
            if self.grid[row][col+1] == 1:
                i = self.convert_indexes(row, col)
                j = self.convert_indexes(row, col+1)
                self.uf.union(i, j)
        
        # check above
        if row != 0:
            if self.grid[row-1][col] == 1:
                i = self.convert_indexes(row, col)
                j = self.convert_indexes(row-1, col)
                self.uf.union(i, j)
        
        # check below
        if row != self.size - 1:
            if self.grid[row+1][col] == 1:
                i = self.convert_indexes(row, col)
                j = self.convert_indexes(row+1, col)
                self.uf.union(i, j)


    def isOpen(self, row, col) -> bool:
        if row < 0 or col < 0 or row >= self.size or col >= self.size:
            raise ValueError
        return True if self.grid[row][col] == 1 else False


    def isFull(self, row, col) -> bool:
        if row < 0 or col < 0 or row > self.size or col > self.size:
            raise ValueError
        i = self.convert_indexes(row, col)
        if self.uf.connected(i, self.size**2 + 1):
            return True
        return False


    def numberOfOpenSites(self) -> int:
        return self.num_of_open_sites


    def percolates(self) -> bool:
        if self.isFull(self.size, 0):
            self.threshold = self.num_of_open_sites / self.size**2
            return True
        return False


class PercolationStats:
    def __init__(self, n, trials) -> None:
        if n <= 0 or trials <= 0:
            raise ValueError
        self.size = n
        self.trials = trials
        self.thresholds = []
        start_time = time.time()
        j = 1

        for i in range(trials):
            perc = Percolation(n)
            random_coordinates = random.sample(range(perc.size**2), perc.size**2)
            i = 0
            progress_bar(j, trials)
            while not perc.percolates():
                perc.open(random_coordinates[i] // perc.size, random_coordinates[i] % perc.size)
                i += 1
            self.thresholds.append(perc.threshold)
            j += 1

        finish_time = time.time()
        self.time_trialing = finish_time - start_time


    def mean(self) -> float:
        thresholds_sum = 0
        for i in self.thresholds:
            thresholds_sum += i
        return thresholds_sum / self.trials


    def stddev(self) -> float:
        mean = self.mean()
        numerator = 0
        for i in self.thresholds:
            numerator += (i - mean)**2
        return math.sqrt(numerator / (self.trials - 1))


    def confidenceLo(self):
        return self.mean() - (1.96 * self.stddev() / math.sqrt(self.trials))


    def confidenceHi(self):
        return self.mean() + (1.96 * self.stddev() / math.sqrt(self.trials))
    

    def print_time_taken(self):
        print(f"Time trialing: {int(self.time_trialing // 60)} minutes {round(self.time_trialing % 60)} seconds")


def progress_bar(progress, total):
    percent = 100 * (progress / float(total))
    bar = '█' * int(percent) + '-' * (100 - int(percent))
    print(f"\r|{bar}| {percent:.2f}%", end="\r")
    if progress == total:
        print()


start_time = time.time()

prc_sts = PercolationStats(200, 10000)
print()
print(f"{prc_sts.size}x{prc_sts.size} grid, {prc_sts.trials} trials:")
print(f'''mean\t\t\t = {prc_sts.mean()}
stddev\t\t\t = {prc_sts.stddev()}
95% confidence interval\t = [{prc_sts.confidenceLo()}, {prc_sts.confidenceHi()}]''')

finish_time = time.time()
total_time = finish_time - start_time
prc_sts.print_time_taken()
print(f"Total time spend: {int(total_time // 60)} minutes {round(total_time % 60)} seconds\n")
