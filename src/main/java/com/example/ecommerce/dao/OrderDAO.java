package com.example.ecommerce.dao;

import com.example.ecommerce.model.*; import com.example.ecommerce.util.DBConnection; import java.math.BigDecimal; import java.sql.*; import java.util.*;

public class OrderDAO {
    private Order map(ResultSet rs) throws Exception{Order o=new Order();o.setId(rs.getInt("id"));o.setUserId(rs.getInt("user_id"));try{o.setCustomerName(rs.getString("customer_name"));}catch(Exception ignored){}o.setOrderDate(rs.getTimestamp("order_date"));o.setTotalAmount(rs.getBigDecimal("total_amount"));o.setStatus(rs.getString("status"));return o;}
    public int createOrder(int userId, List<CartItem> cart) throws Exception{
        String orderSql="INSERT INTO orders(user_id,total_amount,status) VALUES(?,?,'Beklemede')";
        String itemSql="INSERT INTO order_items(order_id,product_id,quantity,unit_price,subtotal) VALUES(?,?,?,?,?)";
        String stockSql="UPDATE products SET stock=stock-? WHERE id=? AND stock>=?";
        BigDecimal total=cart.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        try(Connection c=DBConnection.getConnection()){
            c.setAutoCommit(false);
            try(PreparedStatement op=c.prepareStatement(orderSql,Statement.RETURN_GENERATED_KEYS)){
                op.setInt(1,userId);op.setBigDecimal(2,total);op.executeUpdate();ResultSet keys=op.getGeneratedKeys();keys.next();int orderId=keys.getInt(1);
                for(CartItem ci:cart){try(PreparedStatement sp=c.prepareStatement(stockSql)){sp.setInt(1,ci.getQuantity());sp.setInt(2,ci.getProduct().getId());sp.setInt(3,ci.getQuantity()); if(sp.executeUpdate()==0) throw new Exception("Stok yetersiz: "+ci.getProduct().getName());}
                    try(PreparedStatement ip=c.prepareStatement(itemSql)){ip.setInt(1,orderId);ip.setInt(2,ci.getProduct().getId());ip.setInt(3,ci.getQuantity());ip.setBigDecimal(4,ci.getProduct().getPrice());ip.setBigDecimal(5,ci.getSubtotal());ip.executeUpdate();}}
                c.commit();return orderId;
            }catch(Exception e){c.rollback();throw e;}
        }
    }
    public List<Order> findByUser(int userId) throws Exception{List<Order> l=new ArrayList<>();try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC")){ps.setInt(1,userId);ResultSet rs=ps.executeQuery();while(rs.next())l.add(map(rs));}return l;}
    public List<Order> findAll() throws Exception{List<Order> l=new ArrayList<>();String sql="SELECT o.*, u.full_name customer_name FROM orders o JOIN users u ON o.user_id=u.id ORDER BY o.order_date DESC";try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet rs=s.executeQuery(sql)){while(rs.next())l.add(map(rs));}return l;}
    public Order findById(int id) throws Exception{String sql="SELECT o.*, u.full_name customer_name FROM orders o JOIN users u ON o.user_id=u.id WHERE o.id=?";try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,id);ResultSet rs=ps.executeQuery();if(!rs.next())return null;Order o=map(rs);o.setItems(findItems(c,id));return o;}}
    private List<OrderItem> findItems(Connection c,int orderId)throws Exception{List<OrderItem> l=new ArrayList<>();String sql="SELECT oi.*, p.name product_name FROM order_items oi JOIN products p ON oi.product_id=p.id WHERE oi.order_id=?";try(PreparedStatement ps=c.prepareStatement(sql)){ps.setInt(1,orderId);ResultSet rs=ps.executeQuery();while(rs.next()){OrderItem i=new OrderItem();i.setId(rs.getInt("id"));i.setOrderId(rs.getInt("order_id"));i.setProductId(rs.getInt("product_id"));i.setProductName(rs.getString("product_name"));i.setQuantity(rs.getInt("quantity"));i.setUnitPrice(rs.getBigDecimal("unit_price"));i.setSubtotal(rs.getBigDecimal("subtotal"));l.add(i);}}return l;}
    public void updateStatus(int id,String status)throws Exception{try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement("UPDATE orders SET status=? WHERE id=?")){ps.setString(1,status);ps.setInt(2,id);ps.executeUpdate();}}
    public int countAll() throws Exception{try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM orders")){r.next();return r.getInt(1);}}
    public int countPending() throws Exception{try(Connection c=DBConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM orders WHERE status='Beklemede'")){r.next();return r.getInt(1);}}
}
