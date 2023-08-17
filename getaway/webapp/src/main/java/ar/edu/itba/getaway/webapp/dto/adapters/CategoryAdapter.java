package ar.edu.itba.getaway.webapp.dto.adapters;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;

public class CategoryAdapter extends XmlAdapter<CategoryAdapter.CategoryAdapterDto, HashMap<String, String>> {

    @XmlRootElement
    public static class CategoryAdapterDto {
        public long id;
        public String name;
    }

    @Override
    public HashMap<String, String> unmarshal(CategoryAdapterDto p) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(p.id));
        map.put("name", p.name);
        return map;
    }

    @Override
    public CategoryAdapterDto marshal(HashMap<String, String> v) {
        CategoryAdapterDto p = new CategoryAdapterDto();
        p.id = Long.parseLong(v.get("id"));
        p.name = v.get("name");
        return p;
    }
}