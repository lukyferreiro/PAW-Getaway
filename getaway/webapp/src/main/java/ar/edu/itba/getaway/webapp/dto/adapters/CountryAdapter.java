package ar.edu.itba.getaway.webapp.dto.adapters;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;

public class CountryAdapter extends XmlAdapter<CountryAdapter.CountryAdapterDto, HashMap<String, String>> {

    @XmlRootElement
    public static class CountryAdapterDto {
        public long id;
        public String name;
    }

    @Override
    public HashMap<String, String> unmarshal(CountryAdapterDto p) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(p.id));
        map.put("name", p.name);
        return map;
    }

    @Override
    public CountryAdapterDto marshal(HashMap<String, String> v) {
        CountryAdapterDto p = new CountryAdapterDto();
        p.id = Long.parseLong(v.get("id"));
        p.name = v.get("name");
        return p;
    }
}