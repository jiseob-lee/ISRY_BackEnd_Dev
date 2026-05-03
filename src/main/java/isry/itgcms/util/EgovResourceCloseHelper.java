package isry.itgcms.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Wrapper;

/**
 * Utility class  to support to close resources
 * @author Vincent Han
 * @since 2014.09.18
 * @version 1.0
 * @see
 * </pre>
 */
public class EgovResourceCloseHelper {
	/**
	 * Resource close 처리.
	 * @param resources
	 */
	public static void close(Closeable  ... resources) {
		for (Closeable resource : resources) {
			if (resource != null) {
				try {
					resource.close();
				} catch (IOException e) {
					//System.out.println(e.getMessage());
				} catch (Exception e) {
					//System.out.println(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * JDBC 관련 resource 객체 close 처리
	 * @param objects
	 */
	public static void closeDBObjects(Wrapper ... objects) {
		for (Object object : objects) {
			if (object != null) {
				if (object instanceof ResultSet) {
					try {
						((ResultSet)object).close();
					} catch (SQLException e) {
						//System.out.println(e.getMessage());
					} catch (Exception e) {
						//System.out.println(e.getMessage());
					}
				} else if (object instanceof Statement) {
					try {
						((Statement)object).close();
					} catch (SQLException e) {
						//System.out.println(e.getMessage());
					} catch (Exception e) {
						//System.out.println(e.getMessage());
					}
				} else if (object instanceof Connection) {
					try {
						((Connection)object).close();
					} catch (SQLException e) {
						//System.out.println(e.getMessage());
					} catch (Exception e) {
						//System.out.println(e.getMessage());
					}
				} else {
					throw new IllegalArgumentException("Wrapper type is not found : " + object.toString());
				}
			}
		}
	}
	
	/**
	 * Socket 관련 resource 객체 close 처리
	 * @param objects
	 */
	public static void closeSocketObjects(Socket socket, ServerSocket server) {
		if (socket != null) {
			try {
				socket.shutdownOutput();
			} catch (IOException e) {
				//System.out.println(e.getMessage());
			} catch (Exception e) {
				//System.out.println(e.getMessage());
			}
			
			try {
				socket.close();
			} catch (IOException e) {
				//System.out.println(e.getMessage());
			} catch (Exception e) {
				//System.out.println(e.getMessage());
			}
		}
		
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				//System.out.println(e.getMessage());
			} catch (Exception e) {
				//System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 *  Socket 관련 resource 객체 close 처리
	 *  
	 * @param sockets
	 */
	public static void closeSockets(Socket ... sockets) {
		for (Socket socket : sockets) {
			if (socket != null) {
				try {
					socket.shutdownOutput();
				} catch (IOException e) {
					//System.out.println(e.getMessage());
				} catch (Exception e) {
					//System.out.println(e.getMessage());
				}
				
				try {
					socket.close();
				} catch (IOException e) {
					//System.out.println(e.getMessage());
				} catch (Exception e) {
					//System.out.println(e.getMessage());
				}
			}
		}
	}
}