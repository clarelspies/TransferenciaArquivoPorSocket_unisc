package br.com.clarel;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket extends Thread {
	public static void main(String[] args) throws IOException {
		int bytesRead;

		// Abre o socket do servidor na porta especificada
		ServerSocket serverSocket = new ServerSocket(2016);

		while (true) {
			Socket socketDoCliente = serverSocket.accept();

			InputStream in = socketDoCliente.getInputStream();

			DataInputStream dataRecebidaDoCliente = new DataInputStream(in);

			String fileName = dataRecebidaDoCliente.readUTF();
			OutputStream output = new FileOutputStream("/home/clarel/" + fileName);
			long tamanhoDoArquivo = dataRecebidaDoCliente.readLong();
			byte[] buffer = new byte[1024];
			while (tamanhoDoArquivo > 0 && (bytesRead = dataRecebidaDoCliente.read(buffer, 0, (int) Math.min(buffer.length, tamanhoDoArquivo))) != -1) {
				output.write(buffer, 0, bytesRead);
				tamanhoDoArquivo -= bytesRead;
			}

			// Fecha o arquivo que foi escrito e a conexão com o cliente
			in.close();
			dataRecebidaDoCliente.close();
			output.close();
		}
	}
}