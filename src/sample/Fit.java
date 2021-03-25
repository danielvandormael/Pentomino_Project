package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Fit {
	public static int i;
	public static int j;
	public static int[][] solutionset = new int[200000][5];
	public static int[][] solutionsetorder;
	
	public static void calculateharder(int l, boolean perfectfit) {
		
		double alreadyfilled = 120 - 5*l;
		double alreadyB = alreadyfilled/5;
		double alreadyC = alreadyfilled/5;
		
		for(double t = 0; t<=alreadyB; t++) {
			for(double p = 0; p<=((120-(5*l)-(5*t))/5); p++) {
				double solution = 5*l + 5*t + 5*p;
				double score = 3*l + 5*t + 4*p;
				if(perfectfit){
					if(solution == 120) {
						solutionset[i][0] = (int)l;
						solutionset[i][1] = (int)t;
						solutionset[i][2] = (int)p;
						solutionset[i][3] = (int)score;
						solutionset[i][4] = (int)solution;
						//ystem.out.println(solutionset[i][0] + " " + solutionset[i][1] + " " + solutionset[i][2] + " " + solutionset[i][3] + " " + solutionset[i][4]);						
						i++;
					}
				}else {
					if(solution<=1320) {
						solutionset[i][0] = (int)l;
						solutionset[i][1] = (int)t;
						solutionset[i][2] = (int)p;
						solutionset[i][3] = (int)score;
						solutionset[i][4] = (int)solution;
						//System.out.println(solutionset[i][0] + " " + solutionset[i][1] + " " + solutionset[i][2] + " " + solutionset[i][3] +" " + solutionset[i][4]);
						i++;
					}
				}
			}
		}
		l--;
		if(l< 0) return;
		calculateharder(l,perfectfit);		
	}
	public static void ordemax(){
		ArrayList<int []> reoder =new ArrayList<int []>();
		for (int i =0;i<solutionset.length;i++){
			reoder.add(solutionset[i]);
		}
		for (int i=0;i<reoder.size();i++){
			int [] a = reoder.get(i);
			if(a[3]==0 || a[4]==0){
				reoder.remove(i);
			}
		}
		int [] tr = new int [reoder.size()];
		for (int i =0;i<tr.length;i++){
			int [] a = reoder.get(i);
			tr[i]=a[3];
		}
		Arrays.sort(tr);
		solutionsetorder =new int [reoder.size()][5];
		for (int i=0;i<tr.length;i++){
			for (int y=0;y<reoder.size();y++){
				int [] a = reoder.get(y);
				if (tr[i]==a[3]){
					solutionsetorder[i][0]=a[0];
					solutionsetorder[i][1]=a[1];
					solutionsetorder[i][2]=a[2];
					solutionsetorder[i][3]=a[3];
					solutionsetorder[i][4]=a[4];
					System.out.print("score "+solutionsetorder[i][3]);
					System.out.print(" c "+solutionsetorder[i][2]);
					System.out.print(" b "+solutionsetorder[i][1]);
					System.out.print(" a "+solutionsetorder[i][0]);
					System.out.println("");

					break;
				}
			}
		}
        
	}


}