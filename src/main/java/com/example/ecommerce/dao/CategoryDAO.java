package com.example.ecommerce.dao;

import com.example.ecommerce.model.Category; import com.example.ecommerce.util.DBConnection; import java.sql.*; import java.util.*;

public class CategoryDAO {
    private Category map(ResultSet rs) throws Exception{Category c=new Category(); c.setId(rs.getInt("id")); c.setName(rs.getString("name")); c.setDescription(rs.getString("description")); c.setActive(rs.getBoolean("is_active")); return c;}
    public List<Category> findAll() throws Exception{List<Category> l=new ArrayList<>(); try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet rs=s.executeQuery("SELECT * FROM categories ORDER BY name")){while(rs.next())l.add(map(rs));} return l;}
    public List<Category> findActive() throws Exception{List<Category> l=new ArrayList<>(); try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet rs=s.executeQuery("SELECT * FROM categories WHERE is_active=1 ORDER BY name")){while(rs.next())l.add(map(rs));} return l;}
    public Category findById(int id) throws Exception{try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM categories WHERE id=?")){ps.setInt(1,id);ResultSet rs=ps.executeQuery();return rs.next()?map(rs):null;}}
    public void save(Category cat) throws Exception{String sql=cat.getId()==0?"INSERT INTO categories(name,description,is_active) VALUES(?,?,?)":"UPDATE categories SET name=?,description=?,is_active=? WHERE id=?"; try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,cat.getName());ps.setString(2,cat.getDescription());ps.setBoolean(3,cat.isActive()); if(cat.getId()!=0)ps.setInt(4,cat.getId()); ps.executeUpdate();}}
    public void deactivate(int id) throws Exception{try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement("UPDATE categories SET is_active=0 WHERE id=?")){ps.setInt(1,id);ps.executeUpdate();}}
    public int countAll() throws Exception{try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM categories")){r.next();return r.getInt(1);}}
}
