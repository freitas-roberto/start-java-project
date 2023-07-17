package br.eti.freitas.startproject.infrastructure.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import br.eti.freitas.startproject.infrastructure.util.LowerCaseClassNameResolver;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class CustomError {

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSHH")
    private Date timestamp;
    private String type;
	private HttpStatus status;
	private int code;
	private String title;
	private String details;
    private String path;
    private List<SubError> errors;

    private CustomError() {
        timestamp = new Date(); 
    }

    public CustomError(HttpStatus status) {
        this();
        this.status = status;
        this.code = status.value();
        this.title = status.getReasonPhrase();
    }

    public CustomError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.code = status.value();
        this.title = status.getReasonPhrase();
        this.details = ex.getMessage();
    }

    public CustomError(HttpStatus status, String details, Throwable ex) {
        this();
        this.status = status;
        this.code = status.value();
        this.title = status.getReasonPhrase();
        this.details = details;
    }

    public CustomError(HttpStatus status, String details, String path, Throwable ex) {
        this();
        this.status = status;
        this.code = status.value();
        this.title = status.getReasonPhrase();
        this.details = details;
        this.path = path;
    }

    public CustomError(HttpStatus status, String type, String details, String path, Throwable ex) {
        this();
        this.type = type;
        this.status = status;
        this.code = status.value();
        this.title = status.getReasonPhrase();
        this.details = details;
        this.path = path;
    }

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

    public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}


	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public List<SubError> getErrors() {
		return errors;
	}

	public void setErrors(List<SubError> errors) {
		this.errors = errors;
	}
	
    private void addSubError(SubError error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

}
