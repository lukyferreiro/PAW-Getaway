package ar.edu.itba.getaway.webapp.dto.adapters;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;

public class UserAdapter extends XmlAdapter<UserAdapter.UserAdapterDto, HashMap<String, String>> {

    @XmlRootElement
    public static class UserAdapterDto {
        public long id;
        public String name;
        public String surname;
    }

    @Override
    public HashMap<String, String> unmarshal(UserAdapterDto p) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(p.id));
        map.put("name", p.name);
        map.put("surname", p.surname);
        return map;
    }

    @Override
    public UserAdapterDto marshal(HashMap<String, String> v) {
        UserAdapterDto p = new UserAdapterDto();
        p.id = Long.parseLong(v.get("id"));
        p.name = v.get("name");
        p.surname = v.get("surname");
        return p;
    }
}