package com.example.duan1_appbandoan.DataBase;

public class Data_SQLite {
    public static final String INSERT_USER = "insert into user(userName,password,email,address,phone,role) values" +
            "('huy', 'huy', 'huy11@gmaiil.com','CB','1234567890',0)," +
            "('hieu', 'hieu12345', 'hieudd@gmaiil.com','HN','1234567890',0),";

    public static final String INSERT_PRODUCT =
            "INSERT INTO product(name, description, total_sale, id_Review, id_Category, image, price) VALUES " +
                    "('Bánh Mì Pate', 'Bánh mì nướng kẹp pate', 25000, 1, 1, 'banh_mi_pate', 1000), " +
                    "('Bánh Mì Pate, Trứng', 'Bánh mì nướng kẹp pate và trứng', 30000, 2, 1, 'banh_mi_pate_trung', 1000), " +
                    "('Xôi Xéo', 'Xôi xéo truyền thống', 10000, 3, 2, 'xoi_xeo', 1000), " +
                    "('Xôi Xéo Chim Cút', 'Xôi xéo ăn kèm chim cút', 30000, 4, 2, 'xoi_xeo_chim_cut', 1000), " +
                    "('Cơm Rang Dưa Bò', 'Cơm rang với thịt bò và dưa chua', 40000, 5, 3, 'com_rang_dua_bo', 1000), " +
                    "('Bún Chả Hà Nội', 'Bún chả Hà Nội với chả nướng thơm lừng', 35000, 6, 4, 'bun_cha', 1000), " +
                    "('Bún Bò Huế', 'Bún bò Huế chính gốc', 45000, 7, 4, 'bun_bo_hue', 1000)";

    public static final String INSERT_CATEGORY = "INSERT INTO category(id_Category,name_Category) VALUES" +
            "('1','Bánh Mì')," +
            "('2','Xôi')," +
            "('3','Cơm Rang')," +
            "('4','Bún')";
}
