package com.streamacho.meeting.util.elasticsearch.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ElasticsearchQueryMapper {

     public static String toString(Collection<? extends Enum> source) {
          return source.stream()
                       .map(ElasticsearchQueryMapper::toLowerCaseOrEmptyString)
                       .collect(Collectors.joining("\", \"", "[\"", "\"]"));
     }

     private static String toLowerCaseOrEmptyString(Enum status) {
          return isNull(status) ? "" : status.name().toLowerCase();
     }
}
