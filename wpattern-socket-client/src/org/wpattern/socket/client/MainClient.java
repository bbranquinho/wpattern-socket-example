package org.wpattern.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClient {

	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		// 1. Eh feita a leitura do nome que identifica o cliente. Em geral para cada cliente precisamos colocar um nome diferente.
		System.out.println("Qual o nome que identifica o cliente?");

		String clientName = reader.readLine();

		// 2. Realizamos a conexao com o servidor informando o nome da instancia deste cliente, o IP (endereco) do servidor e a porta do servidor.
		Client client = new Client();

		// 2.1. Leitura do endereco em que esta o servidor. Para testes na mesma maquina colocamos "localhost" ou "127.0.0.1".
		System.out.println("Quald o endereco (IP) que esta o servidor?");

		String hostname = reader.readLine();

		// 2.2. Leitura da porta em que esta o servidor.
		System.out.println("Qual a porta que esta o servidor?");

		Integer port = Integer.parseInt(reader.readLine());

		client.startClient(clientName, hostname, port);

		// 3. Fazemos a leitura das mensagens que seroa enviadas para o servidor.
		System.out.println("Qual a mensagem que sera enviada?");

		String message = reader.readLine();

		// 4. Enquanto a mensagem não for nula ou vazia é feita a leitura de mensagens que serão enviadas.
		while ((message != null) && !message.trim().isEmpty()) {
			// 5. Enviamos a mensagem para o servidor.
			client.sendMessage(message);

			System.out.println("Qual a mensagem que sera enviada?");

			// 6. Fazemos mais uma leitura de outra mensagem que deve ser enviada.
			message = reader.readLine();
		}
	}

}
