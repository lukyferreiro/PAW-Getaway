package ar.edu.itba.getaway.webapp.dto.adapters;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;

public class ExperienceAdapter extends XmlAdapter<ExperienceAdapter.ExperienceAdapterDto, HashMap<String, String>> {

    @XmlRootElement
    public static class ExperienceAdapterDto {
        public long id;
        public String name;
    }

    @Override
    public HashMap<String, String> unmarshal(ExperienceAdapterDto p) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(p.id));
        map.put("name", p.name);
        return map;
    }

    @Override
    public ExperienceAdapterDto marshal(HashMap<String, String> v) {
        ExperienceAdapterDto p = new ExperienceAdapterDto();
        p.id = Long.parseLong(v.get("id"));
        p.name = v.get("name");
        return p;
    }
}