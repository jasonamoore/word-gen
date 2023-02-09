package util;

public class Distribution {

	private int cursor = 0; // current index to add items into
	private double sum = 0; // current maximum value of the distribution
	
	private double[][] dist;
	
	public Distribution() {
		dist = new double[10][3];
	}
	
	public Distribution(int initialSize) {
		dist = new double[initialSize][3];
	}
	
	public void addRange(double[] data) {
		if (data.length != 2) return;
		if (data[0] <= 0) return;
		
		// increase size if needed
		if (cursor >= dist.length) {
			double[][] newdist = new double[dist.length * 2][3];
			System.arraycopy(dist, 0, newdist, 0, dist.length);
			dist = newdist;
		}
		
		dist[cursor][0] = sum; // set range minimum to the current sum
		dist[cursor][1] = data[0] + sum; // set range maximum to the current sum + data range size
		dist[cursor][2] = data[1]; // set value
		
		sum += data[0]; // increase sum by data range size
		cursor++;
	}
	
	public double find(double value) {
		int mindex = 0;
		int maxdex = cursor - 1;
		int middex = (maxdex - mindex) / 2 + mindex;
		double[] middle = dist[middex];
		while (!(middle[0] < value && middle[1] >= value) && mindex != maxdex) {
			if (middle[0] > value) {
				maxdex = middex;
				middex = (int) Math.floor((maxdex - mindex) / 2.0) + mindex;
			}
			else if (middle[1] < value) {
				mindex = middex;
				middex = (int) Math.ceil((maxdex - mindex) / 2.0) + mindex;
			}
			middle = dist[middex];
		}
		if (middle[0] < value && middle[1] >= value)
			return middle[2]; // found the value!
		return -1; // not found
	}
	
	public double getRandom() {
		if (sum == 0) return -1;
		return find(Math.random() * sum);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cursor; i++) {
			sb.append(String.format("Key %f: %f-%f", dist[i][2], dist[i][0], dist[i][1]));
			if (i != cursor - 1) sb.append(", ");
		}
		return sb.toString();
	}

	public boolean isEmpty() {
		return sum == 0;
	}
	
}
