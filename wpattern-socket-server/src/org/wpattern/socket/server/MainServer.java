package org.wpattern.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainServer {

	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		// 1. Realizamos a leitura da porta em que sera aberta pelo servidor.
		System.out.println("Qual a porta em que sera executado (sugestao da porta 6681) o servidor?");

		int port = Integer.parseInt(reader.readLine());

		// 2. Criamos o servidor e iniciamos ele na porta que foi lida.
		Server server = new Server();

		server.startServer(port);
	}

}
