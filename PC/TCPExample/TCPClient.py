
import socket

HOST = '127.0.0.1'  # The server's hostname or IP address
PORT = 6789        # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    while True:
        message = input()
        if message=='-1': break
        message += '\n'
        s.sendall(bytes(message, 'utf-8'))
        s.timeout(5.0)
        data = s.recv(1024) #.decode('utf-8')
        data.decode('utf-8')
        print('Received', repr(data))