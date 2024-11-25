package dao;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public interface userDao {
    void insert();
    List getUserList();
    Map getUserMap();
    Set getUserSet();
    Properties getUserPros();
}
