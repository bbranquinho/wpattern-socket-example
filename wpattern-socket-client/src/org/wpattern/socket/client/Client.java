package org.wpattern.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.wpattern.socket.utils.MessageBean;

public class Client {

	// Variavel usada para enviar mensagens para o servidor.
	private ObjectOutputStream outputStream;

	// Variavel usada para receber mensagens do servidor.
	private ObjectInputStream inputStream;

	// Nome do cliente.
	private String clientName;

	@SuppressWarnings("resource")
	public void startClient(String clientName, String hostname, int port) {
		// 2.1. O cliente eh iniciado.
		this.clientName = clientName;

		try {
			// 2.2. Conecta com o servidor em um daterminado endereco e porta.
			Socket socket = new Socket(hostname, port);

			// 2.3. Recuperada da conexao (socket) as variaveis usadas para enviar e receber mensagens.
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());

			// 2.4. Iniciamos uma thread que eh responsavel por tratar da leitura das mensagens enviadas pelo servidor.
			new Thread(new ClientRunnable()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) throws IOException {
		// 3.1. Eh enviada a mensagem para o servidor.
		this.outputStream.writeObject(new MessageBean(clientName, message));
	}

	private class ClientRunnable implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					// 2.5. Eh responsavel por ficar lendo mensagens enviadas pelo servidor.
					MessageBean message = (MessageBean) Client.this.inputStream.readObject();

					// 2.6. Cada mensagens lida fazemos o que desejar com ela, neste caso apenas imprimimos no console.
					System.out.println("Client receveid from server the message: " + message);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e.getMessage());
					break;
				}
			}
		}

	}

}
