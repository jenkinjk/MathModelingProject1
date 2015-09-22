public class sudukoSolver {
	static long total = 0;
	private static long start;
	static boolean done = false;

	public static void main(String[] args) {
		SudokoGenerator sg = new SudokoGenerator();
		intObject[][] sudoko = sg.generate();

		// intObject[][] sudoko = new intObject[9][9];
		// for (int i = 0; i < 9; i++) {
		// for (int j = 0; j < 9; j++) {
		// sudoko[i][j] = new intObject(0, false);
		// }
		// }
		// sudoko[0][2].setPermData(9);
		// sudoko[0][4].setPermData(4);
		// sudoko[1][1].setPermData(2);
		// sudoko[1][5].setPermData(3);
		// sudoko[1][8].setPermData(4);
		// sudoko[2][0].setPermData(3);
		// sudoko[2][6].setPermData(2);
		// sudoko[2][8].setPermData(1);
		// sudoko[3][3].setPermData(3);
		// sudoko[3][5].setPermData(5);
		// sudoko[3][8].setPermData(7);
		// sudoko[4][1].setPermData(3);
		// sudoko[4][7].setPermData(8);
		// sudoko[5][0].setPermData(5);
		// sudoko[5][3].setPermData(9);
		// sudoko[5][5].setPermData(8);
		// sudoko[6][0].setPermData(8);
		// sudoko[6][2].setPermData(4);
		// sudoko[6][8].setPermData(5);
		// sudoko[7][0].setPermData(1);
		// sudoko[7][3].setPermData(2);
		// sudoko[7][7].setPermData(7);
		// sudoko[8][4].setPermData(7);
		// sudoko[8][6].setPermData(1);
		System.out.println("Initial sudoku:");

		printSudoko(sudoko);
		start = System.currentTimeMillis();
		solve(sudoko, 0, 0, true);
		if (!done)
			System.out.println("all " + total + " solutions found in "
					+ (System.currentTimeMillis() - start) + " milliseconds.");
		else
			System.out.println("Completed in "
					+ (System.currentTimeMillis() - start) + " milliseconds.");
	}

	public static void printSudoko(intObject[][] sudoko) {
		for (int l = 0; l < 9; l++) {
			if (l % 3 == 0)
				System.out.println("----------------------");
			for (int n = 0; n < 9; n++) {
				if (n % 3 == 0)
					System.out.print("|");
				System.out.print(sudoko[l][n].getData() + " ");

			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println("----------------------");
		System.out.println();
	}

	private static void setPermDataSym(int i, int j, int val,
			intObject[][] sudoko) {
		sudoko[i][j].setPermData(val);
		sudoko[j][i].setPermData(val);

	}

	public static void solve(intObject[][] sudoko, int i, int j, boolean print) {
		if (sudoko[i][j].permenant)
			continueOn(sudoko, i, j, print);
		else {
			for (int k = 1; k < 10; k++) {
				if (!done) {
					sudoko[i][j].setData(k);
					if (verify(sudoko)) {
						if (i == 8 && j == 8) {
							if (print) {
								printSudoko(sudoko);
							}
							total++;
							if (!uniquenessCheck()) {
								System.out
										.println("The solution is not unique");
								done = true;
								break;
							}
						} else {
							continueOn(sudoko, i, j, print);
						}
					}
				}
			}
			sudoko[i][j].setData(0);
		}
	}

	private static boolean uniquenessCheck() {
		if (!(total < 2)) {
			return false;
		}
		return true;
	}

	private static void continueOn(intObject[][] sudoko, int i, int j,
			boolean print) {
		if (i != 8) {
			solve(sudoko, i + 1, j, print);
		} else {
			if (j == 8)
				System.exit(0);
			solve(sudoko, 0, j + 1, print);
		}
	}

	public static boolean verify(intObject[][] sudoko) {
		boolean returnee = true;
		for (int i = 0; i < 9; i++) {
			returnee = verifyHorizontal(i, sudoko);
			if (!returnee)
				return returnee;
			returnee = verifyVertical(i, sudoko);
			if (!returnee)
				return returnee;
		}
		return verifySquares(sudoko);
	}

	private static boolean verifySquares(intObject[][] sudoko) {
		for (int i = 0; i < 9; i += 3) {
			for (int j = 0; j < 9; j += 3) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						for (int o = 0; o < 3; o++) {
							for (int u = 0; u < 3; u++) {
								if (sudoko[i + k][j + l].getData() == sudoko[i
										+ o][j + u].getData()
										&& (k != o || l != u)
										&& sudoko[i + k][j + l].getData() != 0)
									return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	private static boolean verifyVertical(int i, intObject[][] sudoko) {
		for (int j = 0; j < 9; j++) {
			for (int k = j + 1; k < 9; k++) {
				if (sudoko[j][i].getData() == sudoko[k][i].getData()
						&& sudoko[k][i].getData() != 0)
					return false;
			}
		}
		return true;
	}

	private static boolean verifyHorizontal(int i, intObject[][] sudoko) {
		for (int j = 0; j < 9; j++) {
			for (int k = j + 1; k < 9; k++) {
				if (sudoko[i][j].getData() == sudoko[i][k].getData()
						&& sudoko[i][k].getData() != 0)
					return false;
			}
		}
		return true;
	}

	public static long getTotal() {
		return total;
	}

	public static long uniqueness(intObject[][] sudoko, int i, int j) throws StackOverflowError{
		done = false;
		long result = -2;
		if (sudoko[i][j].permenant)
			result = continueOnUnique(sudoko, i, j);
		else {
			for (int k = 1; k < 10; k++) {
				if (!done) {
					sudoko[i][j].setData(k);
					if (verify(sudoko)) {
						if (i == 8 && j == 8) {
							total++;
							if (!uniquenessCheck()) {
								result = -1;
								done = true;
								return result;
							}
						} else {
							result = continueOnUnique(sudoko, i, j);
						}
					}
				}
			}
			if (!done) {
				result = total;
			}
			sudoko[i][j].setData(0);
		}
		return result;
	}

	private static long continueOnUnique(intObject[][] sudoko, int i, int j) {
		if (i != 8) {
			return uniqueness(sudoko, i + 1, j);
		} else {
			if (j == 8)
				return uniqueness(sudoko, 0, j);
			return uniqueness(sudoko, 0, j + 1);
		}

	}

}
