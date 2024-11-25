package com.example.duan1_appbandoan.DataBase;

public class Data_SQLite {
    public static final String INSERT_USER = "insert into user(userName,password,email,address,phone,role) values" +
            "('huy', 'huy', 'huy11@gmaiil.com','CB','1234567890',0)";

    public static final String INSERT_PRODUCT =
            "INSERT INTO product(name, description, total_sale, id_Review, id_Category, price) VALUES " +
                    "('Bánh Mì Pate', 'Bánh mì nướng kẹp pate', 25000, 1, 1,1000), " +
                    "('Bánh Mì Pate, Trứng', 'Bánh mì nướng kẹp pate và trứng', 30000, 2, 1,1000)," +
                    "('Xôi Xéo', 'Xôi xéo truyền thống', 10000, 3, 2,1000), " +
                    "('Xôi Xéo Chim Cút', 'Xôi xéo ăn kèm chim cút', 30000, 4, 2,1000), " +
                    "('Cơm Rang Dưa Bò', 'Cơm rang với thịt bò và dưa chua', 40000, 5, 3,1000)," +
                    "('Bún Chả Hà Nội', 'Bún chả Hà Nội với chả nướng thơm lừng', 35000, 6, 4,1000)," +
                    "('Bún Bò Huế', 'Bún bò Huế chính gốc', 45000, 7, 4,1000)";

    public static final String INSERT_CATEGORY = "INSERT INTO category(id_Category,name_Category) VALUES" +
            "('1','Bánh Mì')," +
            "('2','Xôi')," +
            "('3','Cơm Rang')," +
            "('4','Bún')";
}
