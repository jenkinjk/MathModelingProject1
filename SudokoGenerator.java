import java.util.Random;

public class SudokoGenerator {

	public intObject[][] generate() {
		int added = 0;
		intObject[][] sudoko = new intObject[9][9];
		intObject[][] tempSudoko = new intObject[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoko[i][j] = new intObject(0, false);
				tempSudoko[i][j] = new intObject(0, false);
			}
		}
		Random r = new Random();
		int i, j, k, l=0,n,d;
		while(true){
			System.out.println("Run "+l);
			i = r.nextInt(9);
			j = r.nextInt(9);
			k = r.nextInt(9) + 1;
			n = r.nextInt(9)+1;
			d= r.nextInt(1);
			if (!sudoko[i][j].permenant) {
				tempSudoko[i][j] = new intObject(k, true);
				if(d==0)
					tempSudoko[8-j][8-i] = new intObject(n, true);
				else tempSudoko[j][i] = new intObject(n, true);
				long start = System.currentTimeMillis();
				long uniqueness = -2;
				try{
					uniqueness = sudukoSolver.uniqueness(tempSudoko,0,0);
				}catch(StackOverflowError e){
					System.out.println("There was an over flow error!");
					System.out.println("i = "+i+" j= "+j+" k= "+k);
					sudukoSolver.printSudoko(tempSudoko);
				}
				System.out.println("Run "+l+" has uniqueness "+uniqueness+" Found in "+(System.currentTimeMillis()-start));
				sudukoSolver.total=0;
				if (sudukoSolver.verify(tempSudoko)&&uniqueness!=0) {
					sudoko[i][j] = new intObject(k, true);
					if(d==0)
					sudoko[8-j][8-i] = new intObject(n, true);
					else sudoko[j][i] = new intObject(n, true);
					added++;
					if(uniqueness>0){ //Found 1 soln
						sudukoSolver.done=false;
						sudukoSolver.total=0;
						sudukoSolver.solve(sudoko,0,0,false);
						if(sudukoSolver.total==1)
						break;
					}
				} else {
					tempSudoko[i][j] = new intObject(0, false); //No solns with the number there
				}
				//sudukoSolver.printSudoko(sudoko);
			}else //System.out.println("Spot already taken");
			l++;
		}
		sudukoSolver.printSudoko(sudoko);
		System.out.println("added " + added);
		return sudoko;

	}
}
