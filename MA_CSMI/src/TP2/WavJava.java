package TP2;

import common.PlotableMA;


import java.util.ArrayList;

public class WavJava implements PlotableMA {

	// to draw the signal
	public int nbPointDrawn = 1000;
	public double firstTimeDrawn = 0; // in second
	public double lastTimeDrawn; // in second

	public WavJava() {
	}

	/**
	 * Constructor to enter samples with an array of doubles
	 *
	 * @param aSamples
	 * @param aFrequency
	 */
	public WavJava(double[] aSamples, long aFrequency, int aBytesPerSample) {

		samples = aSamples;
		frequency = aFrequency;
		nbChannels = 1;
		bytesPerSample = aBytesPerSample;

		// could be changed before plot
		lastTimeDrawn = this.getDuration();
		System.out.println("leng" + samples.length);

	}
	public WavJava(double[][] aSamplesList, long aFrequency, int aBytesPerSample) {

		samplesList = aSamplesList;
		frequency = aFrequency;
		nbChannels = aSamplesList.length;
		bytesPerSample = aBytesPerSample;

		// could be changed before plot
		lastTimeDrawn = this.getDuration();
		System.out.println("leng" + samplesList[0].length);

	}

	/*
	 * The path where the file is read and written
	 */
	public double samples[];
	public double samplesList[][];
	/*
	 * TODO : nowadays, our project only support 1 channel. 1 mono, 2 stereo, 3
	 * ...
	 */
	public int nbChannels;
	/*
	 * it is the frequency of samples : the number of sound measurement per
	 * second
	 */
	public long frequency;

	public double getDuration() {
		if(nbChannels>1){
			return (double)(samplesList[0].length/frequency);
		}
		else {
			return (double) samples.length / frequency;
		}
	}

	/*
	 * = bitsPerSample / 8 must be 1 or 2 or 4
	 */
	public int bytesPerSample; //




	@Override
	public ArrayList<Double> getXs() {

		ArrayList<Double> Xs = new ArrayList<Double>(nbPointDrawn);

		double drawnStep = (lastTimeDrawn - firstTimeDrawn) / nbPointDrawn;
		for (int i = 0; i < nbPointDrawn; i++)
			Xs.add(i * drawnStep);

		return Xs;
	}

	@Override
	public ArrayList<Double> getYs() {

		ArrayList<Double> Ys = new ArrayList<Double>(nbPointDrawn);
		double drawnStep = (lastTimeDrawn - firstTimeDrawn) / nbPointDrawn;
		double integerDrawnStep = (drawnStep * frequency);


		for (int i = 0; i < nbPointDrawn; i++)



			//Ys.add((double) samples[(int) (i * integerDrawnStep)]);


			if ( (i * integerDrawnStep)< (0.9*(frequency*0.5)) || (i * integerDrawnStep)> (0.1*(frequency*0.5)) && (i * integerDrawnStep)==0  ) {
				Ys.add(i,(double) samples[(int) (i * integerDrawnStep)]);
			}
			else {
				Ys.add(i, 0.0);
			}

		return Ys;

	}

	@Override
	public String toString() {
		return "WavJava [nbPointDrawn=" + nbPointDrawn + ", firstTimeDrawn="
				+ firstTimeDrawn + ", lastTimeDrawn=" + lastTimeDrawn
				+ ", nbChannels=" + nbChannels + ", frequency=" + frequency
				+ ", bytesPerSample=" + bytesPerSample + ", duration="
				+ getDuration() + "]";
	}

}