package TP2;
import common.MultiPlotNew;


public class Test {


	public static void main(String[] args) {


		String typeOfTest = "create and export";

		System.out.println(typeOfTest);

		if (typeOfTest.equals("import and plot")) {
			/**
			 * We import a file and plot the signal
			 */
			// the object were the signal is described by doubles between -1 and
			// 1
			// also contains the essential informations
			WavJava wavJava = new WavJava();

			// TODO : changes the path ! depending to your file organisation
			String filePath = "assets/sons/la.wav";
			ImportWav iw = new ImportWav(filePath, wavJava);
			// all informations contains in the .wav
			// with a lot of useless and redundant informations
			//System.out.println(iw);

			MultiPlotNew mp = new MultiPlotNew();
			wavJava.lastTimeDrawn = 5;
			mp.addPlotable("sw", wavJava);
			mp.plotNow();

		}  else if (typeOfTest.equals("import and export and plot")) {
		/**
		 * We import a signal, we divide the pressure and export the modified
		 * signal
		 */
		WavJava wavJava = new WavJava();

		String filePath = "assets/sons/32.wav";
		ImportWav iw = new ImportWav(filePath, wavJava);
		System.out.println(iw);

		for (int i = 0; i < wavJava.samples.length; i++) {
			wavJava.samples[i] *= 0.5;
		}

		// attention à eclipse (le fourbe). Chez moi, il n' est pas
		// automatiquement synchronisé
		// sur le système de fichier. Ainsi, "la2" n' apparaît pas dans
		// eclipse (mais il existe).
		// par contre, pas de problèmes avec intelliJ
		String filePath2 = "assets/sons/out/32-filtred.wav";
		ExportWav ew = new ExportWav(filePath2, wavJava);
		System.out.println(ew);

		MultiPlotNew mp = new MultiPlotNew();
		wavJava.lastTimeDrawn = 2;
		mp.addPlotable("la2", wavJava);
		mp.plotNow();

	}


		else if (typeOfTest.equals("create and export")) {

			long frequency = 44100; // Hz

			double duration = 2; // sec
			double[] samplesA = new double[(int) (frequency * duration)];
			double[] samplesB = new double[(int) (frequency * duration)];


			for (int i = 0; i < samplesA.length; i++)
				samplesA[i] =
						440*Math.sin( 2*  Math.PI * i / frequency)  ;
			WavJava wavJava1 = new WavJava(samplesA, frequency, 2);




			String filePath = "assets/sons/out/laCreatedRERE.wav";

			//new ExportWav(filePath, wavJava);
			ExportWav ew = new ExportWav(filePath, wavJava1);
			System.out.println(ew);
			MultiPlotNew mp = new MultiPlotNew();
			wavJava1.lastTimeDrawn = 2;
		//	wavJava2.lastTimeDrawn = 0.1;
		//	wavJava3.lastTimeDrawn = 0.1;
			mp.addPlotable("1", wavJava1);
		//	mp.addPlotable("2", wavJava2);
		//	mp.addPlotable("1+2", wavJava3);
		//	mp.setJoinType("1", MultiPlotNew.JoinType.bullet,5);
		//	mp.setJoinType("2", MultiPlotNew.JoinType.bullet,5);
			mp.plotNow();

			// now, listen this signal

		}

		else if (typeOfTest.equals("test conversion of unsigned")) {
			ImportExportWavAbstract iew = new ImportExportWavAbstract();

			// the maximum of 2-bytes unsigned-integer
			int i = (int) (Math.pow(2, 16) - 1);
			System.out.println("i=" + i);

			byte[] b = iew.intToByteArray(i);

			System.out.println(b[1] + " " + b[0]); // is -1 -1 which means
													// 11111111 11111111

		}

		else if (typeOfTest.equals("test conversion of signed")) {

			ImportExportWavAbstract iew = new ImportExportWavAbstract();

			System.out.println("look at the maximal value");

			// max of a signed 2octets integer
			System.out.println("max of a signed 2octets integer="
					+ Short.MAX_VALUE);
			// in binary 01111111 11111111
			// it is 2^7-1

			byte[] twoOctets = iew.shortToByteArray(Short.MAX_VALUE);
			System.out.println(twoOctets[0] + " " + twoOctets[1]); // in binary
																	// 11111111
																	// and
																	// 01111111,
																	// in byte
																	// -1 and
																	// 127

			// we recompose the short. We have
			int low = twoOctets[0] & 0xff;
			int high = twoOctets[1] & 0xff;
			short a = (short) (high << 8 | low);

			System.out.println("a=" + a);

			// min of a signed 2octets integer
			System.out.println("min of a signed 2octets integer="
					+ Short.MIN_VALUE);
			// in binary 10000000 00000000
			// it is - 2^7

			twoOctets = iew.shortToByteArray(Short.MIN_VALUE);
			System.out.println(twoOctets[0] + " " + twoOctets[1]); // in binary
																	// 00000000
																	// and
																	// 10000000,
																	// in byte 0
																	// and -128

			// we recompose the short. We have
			low = twoOctets[0] & 0xff;
			high = twoOctets[1] & 0xff;
			a = (short) (high << 8 | low);

			System.out.println("a=" + a);

		}

	}

}
