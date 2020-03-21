package local.happysixplus.backendcodeanalysis.po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;

import org.springframework.util.StringUtils;

public class LongListToStringConverter implements AttributeConverter<List<Long>, String> {
    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null || attribute.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        attribute.stream().limit(attribute.size() - 1).forEach(s -> sb.append(s).append(","));
        sb.append(attribute.get(attribute.size() - 1));
        return sb.toString();
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData))
            return new ArrayList<>();
        String[] data = dbData.split(",");
        return Arrays.stream(data).map(Long::valueOf).collect(Collectors.toList());
    }
}