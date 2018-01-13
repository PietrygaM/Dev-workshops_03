package pl.coderslab.warsztat3.model;

// W tej klasie będziemy już przechwytywać wyjątek z klasy solutionDao
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.warsztat3.db.DbUtil;

public class Solution {

		public static List<SolutionWithAuthor> loadAllWithAuthor(int count) {
			
			Connection conn;
			List<SolutionWithAuthor> result = new ArrayList<>();
			
			try {
				conn = DbUtil.getConn();
				result = SolutionDao.loadAllWithAuthor(conn, count);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;

		}
	
	
}
