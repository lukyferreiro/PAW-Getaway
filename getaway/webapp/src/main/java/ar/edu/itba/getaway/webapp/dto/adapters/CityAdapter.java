package ar.edu.itba.getaway.webapp.dto.adapters;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;

public class CityAdapter extends XmlAdapter<CityAdapter.CityAdapterDto, HashMap<String, String>> {

    @XmlRootElement
    public static class CityAdapterDto {
        public long id;
        public String name;
    }

    @Override
    public HashMap<String, String> unmarshal(CityAdapterDto p) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(p.id));
        map.put("name", p.name);
        return map;
    }

    @Override
    public CityAdapterDto marshal(HashMap<String, String> v) {
        CityAdapterDto p = new CityAdapterDto();
        p.id = Long.parseLong(v.get("id"));
        p.name = v.get("name");
        return p;
    }
}