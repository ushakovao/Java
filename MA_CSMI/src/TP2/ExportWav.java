package TP2;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class ExportWav extends ImportExportWavAbstract {



	public ExportWav(String aFilePath,WavJava aWavJava){
		super();

		filePath=aFilePath;
		wavJava=aWavJava;


		nbChannels= wavJava.nbChannels;
		fileTypeBlocID="RIFF";
		if(nbChannels>1){
			fileSizeMinus8 = (4 + 4 + 4 + 2 + 2 + 4 + 4 + 2 + 2 + 4 + 4 + wavJava.samplesList[0].length * wavJava.nbChannels * wavJava.bytesPerSample);
		}
		else {
			fileSizeMinus8 = (4 + 4 + 4 + 2 + 2 + 4 + 4 + 2 + 2 + 4 + 4 + wavJava.samples.length * wavJava.nbChannels * wavJava.bytesPerSample);
		}
		format="WAVE";
		formatBlocID="fmt ";
		blockSize=16l;
		audioFormat=1;
		frequency=wavJava.frequency; // must be written in WavJava
		myByteRate=(wavJava.frequency*wavJava.nbChannels*wavJava.bytesPerSample);
		bytePerBlock=wavJava.nbChannels*wavJava.bytesPerSample;
		bitsPerSample=wavJava.bytesPerSample*8; // divided by 8, it is written in WavJava as bytesPerSample
		dataChunkID="data";

		if(nbChannels>1){
			dataSize=wavJava.samplesList[0].length*wavJava.nbChannels*wavJava.bytesPerSample;
		}
		else {
			dataSize=wavJava.samples.length*wavJava.nbChannels*wavJava.bytesPerSample;
		}



		try{

			DataOutputStream outFile  = new DataOutputStream(new FileOutputStream(filePath));

			// write the wav file per the wav file format
			outFile.writeBytes(fileTypeBlocID);					// 00 - RIFF
			outFile.write(intToByteArray((int) fileSizeMinus8), 0, 4);		// 04 - how big is the rest of this file?
			outFile.writeBytes(format);					// 08 - WAVE
			outFile.writeBytes(formatBlocID);					// 12 - fmt
			// 16 pour le format PCM  Etrange qu'ils aient réserver 4 octet pour cela
			outFile.write(intToByteArray((int) blockSize), 0, 4);	// 16 - size of this chunk
			// 1 car on utilise le format PCM
			outFile.write(shortToByteArray((short) audioFormat), 0, 2);		// 20 - what is the audio format? 1 for PCM = Pulse Code Modulation
			outFile.write(shortToByteArray((short) nbChannels ), 0, 2);	// 22 - mono or stereo? 1 or 2?  (or 5 or ???)
			outFile.write(intToByteArray((int) frequency), 0, 4);		// 24 - samples per second (numbers per second)
			outFile.write(intToByteArray((int) myByteRate), 0, 4);		// 28 - bytes per second
			outFile.write(shortToByteArray((short) bytePerBlock), 0, 2);	// 32 - # of bytes in one sample, for all channels
			outFile.write(shortToByteArray((short) bitsPerSample), 0, 2);	// 34 - how many bits in a sample(number)?  usually 16 or 24
			outFile.writeBytes(dataChunkID);	// 36 - data
			outFile.write(intToByteArray((int) dataSize), 0, 4);		// 40 - how big is this data chunk



			data = new byte[(int) dataSize];



			if (wavJava.bytesPerSample==2){
				// the max value of an unsigned-integer on 2bytes
				byte [] twoOctets=new byte[2];
				short a;

				if(nbChannels>1) {
					for (int chan = 0; chan < nbChannels; chan++) {
						for (int i = 0; i < wavJava.samplesList[chan].length; i++) {
							a = (short) (wavJava.samplesList[chan][i] * Short.MAX_VALUE);
							twoOctets = super.shortToByteArray(a);
							data[2*nbChannels * i +2*chan] = twoOctets[0];
							data[2*nbChannels * i +2*chan+ 1] = twoOctets[1];
						}
					}
					outFile.write(data);
					outFile.close();
				}
				else {
					for (int i = 0; i < wavJava.samples.length; i++) {
						a = (short) (wavJava.samples[i] * Short.MAX_VALUE);
						twoOctets = super.shortToByteArray(a);
						data[2 * i] = twoOctets[0];
						data[2 * i + 1] = twoOctets[1];
					}
					outFile.write(data);
					outFile.close();
				}

			}
			else if(wavJava.bytesPerSample==4){
				// the max value of an unsigned-integer on 4bytes
				byte [] fourOctets=new byte[4];
				int b;

				if(nbChannels>1) {
					for (int chan = 0; chan < nbChannels; chan++) {
						for (int i = 0; i < wavJava.samplesList[chan].length; i++) {
							b = (int) (wavJava.samplesList[chan][i] * Integer.MAX_VALUE);
							fourOctets = super.intToByteArray(b);
//							data[2 * (i+chan)] = twoOctets[0];
//							data[2 * (i+chan) + 1] = twoOctets[1];
							data[4*nbChannels * i +2*chan] = fourOctets[0];
							data[4*nbChannels * i +2*chan+ 1] = fourOctets[1];
							data[4*nbChannels * i +2*chan+ 2] = fourOctets[2];
							data[4*nbChannels * i +2*chan+ 3] = fourOctets[3];
						}
					}
					outFile.write(data);
					outFile.close();
				}
				else {
					for (int i = 0; i < wavJava.samples.length; i++) {
						b = (int) (wavJava.samples[i] * Integer.MAX_VALUE);
						fourOctets = super.intToByteArray(b);
						data[4 * i] = fourOctets[0];
						data[4 * i + 1] = fourOctets[1];
						data[4 * i + 2] = fourOctets[2];
						data[4 * i + 3] = fourOctets[3];
					}
					outFile.write(data);
					outFile.close();
				}

			}
			else {
				outFile.write(data);
				outFile.close();
				throw new VincentException("pour l'instant, bytesPerSample ne peut avoir que la valeurs 2");
				//TODO take into account values 1 and 4
			}

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

	}




}