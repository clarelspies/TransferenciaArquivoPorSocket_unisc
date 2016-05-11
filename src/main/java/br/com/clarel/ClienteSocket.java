package br.com.clarel;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClienteSocket {

	public static void main(String[] args) throws IOException {
		// Abre uma conexão socket com o endereço e a porta especificados
		Socket socket = new Socket("127.0.0.1", 2016);

		File arquivoSeraTransferido = new File("/home/clarel/Documentos/apache-tomcat-7.0.69.tar.gz");
		byte[] arrayDeBytesDoArquivo = new byte[(int) arquivoSeraTransferido.length()];

		FileInputStream fis = new FileInputStream(arquivoSeraTransferido);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DataInputStream dis = new DataInputStream(bis);
		dis.readFully(arrayDeBytesDoArquivo, 0, arrayDeBytesDoArquivo.length);

		OutputStream os = socket.getOutputStream();

		// Enviando o NOME e o TAMANHO do arquivo para o server
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeUTF(arquivoSeraTransferido.getName());
		dos.writeLong(arrayDeBytesDoArquivo.length);
		dos.write(arrayDeBytesDoArquivo, 0, arrayDeBytesDoArquivo.length);
		dos.flush();

		// Enviando o ARQUIVO para o server
		os.write(arrayDeBytesDoArquivo, 0, arrayDeBytesDoArquivo.length);
		os.flush();

		// Fecha as conexões com o socket
		os.close();
		dos.close();
		socket.close();
	}

}