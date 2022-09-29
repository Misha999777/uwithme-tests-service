package tk.tcomad.testsystem.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.control.DeepClone;

@MapperConfig(
        componentModel = "spring",
        mappingControl = DeepClone.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MapperConfiguration {

}
