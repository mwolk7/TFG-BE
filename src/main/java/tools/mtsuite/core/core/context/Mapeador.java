package tools.mtsuite.core.core.context;

import java.util.Date;
import java.util.function.Supplier;

import javax.annotation.Resource;

import org.modelmapper.AbstractProvider;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Mapeador {

	@Resource
	@Qualifier(value = "ModelMapperBean")
	private ModelMapper modelMapper;

	Provider<Date> localDateProvider = new AbstractProvider<Date>() {
		@Override
		public Date get() {
			return new Date();
		}
	};

	

	public Object map(Object source, Class<?> destination) {
		TypeMap typeMap = modelMapper.getTypeMap(source.getClass(), destination.getClass());

		if (typeMap == null) {
			modelMapper.createTypeMap(source.getClass(), destination.getClass())
					.setPropertyCondition(Conditions.isNotNull());
		}
		return this.modelMapper.map(source, destination);

	}

	public void map(Object source, Object destination) {
		TypeMap typeMap = modelMapper.getTypeMap(source.getClass(), destination.getClass());

		if (typeMap == null) {
			modelMapper.createTypeMap(source.getClass(), destination.getClass())
					.setPropertyCondition(Conditions.isNotNull());
		}
		this.modelMapper.map(source, destination);

	}

	public Object map(Object source, Class<?> destination, PropertyMap<?, ?> pm) {
		TypeMap typeMap = modelMapper.getTypeMap(source.getClass(), destination.getClass());
		modelMapper.addMappings(pm);
		if (typeMap == null) {
			modelMapper.createTypeMap(source.getClass(), destination.getClass())
					.setPropertyCondition(Conditions.isNotNull());
		}
		return modelMapper.map(source, destination);

	}

	private <S, D> Converter<S, D> converterWithDestinationSupplier(Supplier<? extends D> supplier) {
		return ctx -> ctx.getMappingEngine().map(ctx.create(ctx.getSource(), supplier.get()));
	}

	public void map(Object source, Object destination, PropertyMap<?, ?> pm) {
		TypeMap typeMap = modelMapper.getTypeMap(source.getClass(), destination.getClass());
		modelMapper.addMappings(pm);
		if (typeMap == null) {
			modelMapper.createTypeMap(source.getClass(), destination.getClass())
					.setPropertyCondition(Conditions.isNotNull());
		}
		this.modelMapper.map(source, destination);

	}

	public void addPropertyMap(PropertyMap<?, ?> pm) {
		this.modelMapper.addMappings(pm);

	}

	public void addConverter(Converter<?, ?> cv) {
		modelMapper.addConverter(cv);
	}

}
