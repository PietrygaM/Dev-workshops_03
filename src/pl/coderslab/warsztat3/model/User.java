package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private long id; // Sprawdzić ile jest bitów pomiędzy BIG INT w sql a typem
						// w javie
	private String username;
	private String email;
	private String password;
	private int userGroupId;

	public User() {
		super();
		this.id = 0l;
		this.username = "";
		this.email = "";
		this.password = "";
		this.userGroupId = 0;
	}

	public User(String username, String email, String password) {
		super();
		this.id = 0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	// Korzystamy z zewnętrznej biblioteki szyfrującej
	// Sprawdzić jak zaimportować BCrypt
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void checkPassword(String password) {
		BCrypt.checkpw(password, this.password);
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public long getId() {
		return id;
	}

	// dodatjemy metodę która będzie zapisywać
	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			// jeżeli id jest == 0 to wstawiamy nowy rekord do bazy //15:34
			String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?,?,?,?);"; // Dane
																											// pobieramy
																											// z
																											// pól
																											// obiektu
																											// więc
																											// używamy
																											// "?"
			// Z coonecion bierzemy prepared statement bazując na ciągu znaków
			// który podaliśmy
			// i preparred styatement przypisujemy do zmiennej
			// Musimy się zastanowić jak obsługujemy wyjątek (15:38) tutaj
			// będziemy go rzucać na zewnątrz
			String[] generatedColumns = { "ID" }; // #1
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password); // Musimy jeszcze dodać szyfrowanie
											// hasła
			ps.setInt(4, this.userGroupId);
			ps.executeUpdate();
			// Teraz musimy pobrać id które się wygenerowało #1 będziemy robić
			// tablicę stringów
			ResultSet gk = ps.getGeneratedKeys(); // 15:47

			// Jezeli uda się pobrać element z recordSet (15:48) , Dzień 10 -
			// Rysunek w notatkach
			if (gk.next()) {
				this.id = gk.getLong(1);

			}
			gk.close();
			ps.close();
			// connection a nie zamykamy bo nie my go utworzyliśmy, on przyszedł
			// do nas z zewnątrz i np. nie wiemy czy nie jest wspóldzielony
			// Dlatego nie możemy go zamknąć;
		} else { // Jeżeli projekttu nie ma w bazie to muszę zrobić update
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? WHERE id=?;";
			PreparedStatement ps1 = conn.prepareStatement(sql);
			ps1.setString(1, this.username);
			ps1.setString(2, this.email);
			ps1.setString(3, this.password);
			ps1.setInt(4, this.userGroupId);
			ps1.setLong(5, this.id);
			ps1.executeUpdate();
			ps1.close();
		}

	}

	public static User loadById(Connection conn, long id) throws SQLException {
		{
			String sql = "SELECT username, email, password, user_group_id FROM users where id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				User loadedUser = new User();
				loadedUser.username = resultSet.getString("username");
				loadedUser.email = resultSet.getString("email");
				loadedUser.password = resultSet.getString("password");
				loadedUser.userGroupId = resultSet.getInt("user_group_id");
				return loadedUser;
				
			}
			return null;
		}
	}
		
	static public User[] loadAllUsers(Connection conn) throws SQLException {
			ArrayList<User> users = new ArrayList<User>();
			String sql = "SELECT * FROM users"; PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User loadedUser = new User();
				loadedUser.id = resultSet.getInt("id");
				loadedUser.username = resultSet.getString("username");
				loadedUser.password = resultSet.getString("password");
				loadedUser.email = resultSet.getString("email");
				users.add(loadedUser);
			}
			User[] uArray = new User[users.size()]; 
			
			uArray = users.toArray(uArray);
			return uArray;
		}
		
		
	public void delete(Connection conn) throws SQLException {
		System.out.println("ThisId = "+ this.id);
		if (this.id != 0) {
		String sql = "DELETE FROM users WHERE id= ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setLong(1, this.id);
		preparedStatement.executeUpdate();
		this.id=0;
		}
		}
	

	
	
	
	
		
//		String sql = "";
//		// execute sql
//		User u = new User();
//		return u;

}


