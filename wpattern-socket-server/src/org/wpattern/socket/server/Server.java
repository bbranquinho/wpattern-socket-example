package org.wpattern.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.wpattern.socket.utils.MessageBean;

public class Server {

	private Map<String, ServerRunnable> runnablesByIdentifier = new HashMap<String, Server.ServerRunnable>();

	private int countThread = 0;

	@SuppressWarnings("resource")
	public void startServer(int port) {
		System.out.println("Server started.");

		try {
			// 2.1. Eh iniciado o servidor na porta que tinha sido lida.
			ServerSocket serverSocket = new ServerSocket(port);

			while (true) {
				// 2.2. Esperamos clientes se conectarem no servidor.
				Socket socket = serverSocket.accept();

				// 2.3. Criamos o objeto da classe ServerRunnable que eh responsavel por controlar a conexao do servidor.
				ServerRunnable runnable = new ServerRunnable(System.currentTimeMillis() + "", socket);

				// 2.4. Guardamos a conexao do cliente de acordo com um identificador.
				this.runnablesByIdentifier.put(runnable.getIdentifier(), runnable);

				// 2.5. Uma thread trata de gerenciar a conexao do servidor.
				new Thread(runnable, "Thread WPattern - " + ++this.countThread).start();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private class ServerRunnable implements Runnable {

		private String identifier;
		private ObjectOutputStream output;
		private ObjectInputStream input;

		public ServerRunnable(String identifier, Socket socket) throws IOException {
			// 3. Guardamos as informacoes do servidor.
			this.identifier = identifier;
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());
		}

		@Override
		public void run() {
			while (true) {
				try {
					// 4. O servidor realizar a leitura das mensagens enviadas pelo cliente.
					MessageBean message = (MessageBean) this.input.readObject();

					System.out.println("Server received the message: " + message);

					// 5. A mensagem recebida pelo servidor eh enviada para todos os outros clientes.
					this.sendMessageToAllClients(message);
				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e.getMessage());

					// 6. Ocorreu a desconexao com um cliente, sendo assim, eh removido o cliente da lista de conexoes do servidor.
					runnablesByIdentifier.remove(identifier);
					break;
				}
			}

			System.out.println("Server connection closed.");
		}

		public String getIdentifier() {
			return this.identifier;
		}

		public void sendMessageToAllClients(MessageBean message) throws IOException {
			// 5.1. Uma mensagem eh enviada para todos os clientes.
			for (ServerRunnable serverConection : runnablesByIdentifier.values()) {
				if (serverConection.getIdentifier().compareTo(identifier) != 0) {
					serverConection.output.writeObject(message);
				}
			}
		}

	}

}
