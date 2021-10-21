import struct

MAX_BUFFER_SIZE = 16

def read_n_bytes(conn, n):
    data = bytearray()
    while len(data) < n:
        remain_bytes = n - len(data)
        bufsize = min(remain_bytes, MAX_BUFFER_SIZE)
        d = conn.recv(bufsize)
        data += d
    return bytes(data)

def read_expressions(conn):
    exprs = []
    (n,) = struct.unpack('!h', read_n_bytes(conn, 2))
    for i in range(n):
        (l,) = struct.unpack('!h', read_n_bytes(conn, 2))
        exprs.append(read_n_bytes(conn, l))
    return exprs

def write_expressions(conn, exprs):
    conn.sendall(struct.pack('!h', len(exprs)))
    for expr in exprs:
        conn.sendall(struct.pack('!h', len(expr)))
        conn.sendall(expr)
