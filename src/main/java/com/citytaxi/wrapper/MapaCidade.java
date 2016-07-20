package com.citytaxi.wrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.controller.domain.LocalStatus;

public class MapaCidade {

	public static Local[][] getMapaCidadeCsv() throws IOException {
		//File file2 = new File("./src/main/resources/matriz_cidade.csv");
		createFile();
		File file2 = new File("matriz_cidade.csv");
		
		Local[][] mapa = null;
		
		Scanner scan = new Scanner(file2);
		scan.useDelimiter(",");
		int linha = 0;
		int coluna = 0;
		List<String> linhas = new ArrayList<String>();
		while(scan.hasNext()) {
			String line = scan.nextLine();
			linhas.add(line);
			String[] colunas = line.split(",",-1);
			if(coluna == 0) {
				coluna = colunas.length;
			}
			linha++;
		}
		scan.close();
		
		mapa = popularMapa(linha, coluna, linhas);
		return mapa;
	}

	private static void createFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("matriz_cidade.csv", "UTF-8");
		writer.println(",x,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
		writer.println(",x,,,,,,,,,,,,,,,x,x,x,x,x,x,x,,,,,,,x");
		writer.println(",x,,,,,,,,,,,,,,,x,x,x,x,x,x,x,,,,,,,x");
		writer.println(",x,,,,,,,,,,,,,,,x,x,x,x,x,x,x,,,,,,,x");
		writer.println(",x,,,,,x,,,,,,,,,,x,,,,,,,,,,,,,x");
		writer.println(",x,,,,,x,,,,,,,,,x,x,x,x,x,x,x,x,x,,,,,,x");
		writer.println(",x,,,,,x,,,,,,,,,x,x,,,,,,x,,,,,,,x");
		writer.println(",x,,,,,x,,,,,,,,,x,,x,,,,,x,,,,,,,x");
		writer.println(",x,,,,,x,,,,,,,,,x,,,,,x,,,x,,,,,,");
		writer.println(",x,,,,,x,,,,,,,,,x,,,,,,x,,,x,,,,,");
		writer.println(",,,,,,x,,,,,,,,,x,,,,,,,x,,,x,,,,");
		writer.println(",,,,,,x,,,,,,,,,x,,,,,,,,x,,,,,,");
		writer.println(",,,,,,x,,,,,,,,,x,,,,,,,,,,,,,,");
		writer.println(",,,,,,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,,,");
		writer.println(",,,,,,,,,,,,,x,,x,,,,,,,,,,,,,,");
		writer.println(",,,,,,,,,,,,,x,x,x,x,x,x,x,x,x,x,x,x,x,x,,,x");
		writer.println(",,,,,,,,,,,,,,,x,,,,,,,,,,,,,,");
		writer.println(",,,,,,,,,,,,,,,x,,,,,,,,,,,,,,");
		writer.println("x,x,x,x,x,x,x,x,,,,,,,,x,,,,,,,,,,,,,,");
		writer.println(",,,,,,,,,,,,,,,x,,,,,,,,,,,,,,");
		writer.println(",x,x,x,x,x,x,x,,,,,,,,x,,,,,,,,,,,,,,");
		writer.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
		writer.println("x,x,x,x,x,x,x,x,,,,,,,,,,,,,,,,,,,,,,");
		writer.println(",,,,,,,x,,,,,,,,,,,,,,,,,,,,,,");
		writer.println(",,,,,,,x,,,,,,,,,,,,,,,x,x,x,x,,x,x,x");
		writer.println("x,x,x,x,x,x,x,,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x");
		writer.println(",,,,,,,,,,,,,,,,,,,,,,x,,,,,,,");
		writer.println(",,,,,,,,,,,,,,,,,,,,,,x,,,,,,,");
		writer.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
		writer.println("x,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
		
		writer.close();		
	}

	private static Local[][] popularMapa(int linha, int coluna,
			List<String> linhas) {
		Local[][] mapa;
		mapa = new Local[linha][coluna];
		
		for(int i=0; i<linhas.size(); i++) {
			String line = linhas.get(i);
			String[] colunas = line.split(",",-1);
			for(int j=0;j<colunas.length; j++) {
				LocalStatus status;
				if(colunas[j].equals("x")) {
					status = LocalStatus.bloqueada;
				}else {
					status = LocalStatus.vazia;
				}
				mapa[i][j] = new Local(i,j,status);
			}
		}
		return mapa;
	}
	
}
