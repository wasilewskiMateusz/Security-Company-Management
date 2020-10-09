package pl.lodz.p.it.thesis.scm.util;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class WorkplaceMapper {

    @Mapping( target = "parent", ignore = true )
    public abstract Target toTarget(Source source);

    @AfterMapping
    protected void addBackReference(@MappingTarget TreeNodeDto target) {
        for (TreeNodeDto child : target.getChildren() ) {
            child.setParent( target );
        }
    }
}
