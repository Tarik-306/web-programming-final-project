package com.example.ecommerce.dao;

import com.example.ecommerce.model.User;
import com.example.ecommerce.util.DBConnection;
import com.example.ecommerce.util.PasswordUtil;
import java.sql.*;
import java.util.*;

public class UserDAO {
    private User map(ResultSet rs) throws Exception {
        User u = new User();
        u.setId(rs.getInt("id")); u.setFullName(rs.getString("full_name")); u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password")); u.setPhone(rs.getString("phone")); u.setAddress(rs.getString("address"));
        u.setRole(rs.getString("role")); u.setCreatedAt(rs.getTimestamp("created_at")); return u;
    }
    public boolean emailExists(String email) throws Exception {
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("SELECT id FROM users WHERE email=?")){
            ps.setString(1,email); return ps.executeQuery().next();
        }
    }
    public void register(User u) throws Exception {
        String sql="INSERT INTO users(full_name,email,password,phone,address,role) VALUES(?,?,?,?,?, 'CUSTOMER')";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,u.getFullName()); ps.setString(2,u.getEmail()); ps.setString(3, PasswordUtil.hash(u.getPassword())); ps.setString(4,u.getPhone()); ps.setString(5,u.getAddress()); ps.executeUpdate();
        }
    }
    public User login(String email,String password) throws Exception {
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("SELECT * FROM users WHERE email=? AND password=?")){
            ps.setString(1,email); ps.setString(2,PasswordUtil.hash(password)); ResultSet rs=ps.executeQuery(); return rs.next()?map(rs):null;
        }
    }
    public List<User> findAll() throws Exception {
        List<User> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection(); Statement st=c.createStatement(); ResultSet rs=st.executeQuery("SELECT * FROM users ORDER BY created_at DESC")){
            while(rs.next()) list.add(map(rs));
        } return list;
    }
    public int countAll() throws Exception {try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM users")){r.next();return r.getInt(1);}}
}
