package controler;

import dao.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.bookService;

import java.util.List;

@Controller
public class bookControler {
    @Autowired
    public bookService bk;

    @RequestMapping("/book")
    @ResponseBody
    public List<user> getBook(){
        System.out.println("开始调用getBook方法");
        List<user> userList = bk.getUsersInfo();
        return userList;
    }

}
