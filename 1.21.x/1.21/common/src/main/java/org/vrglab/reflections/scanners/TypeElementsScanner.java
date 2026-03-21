package org.vrglab.reflections.scanners;

import javassist.bytecode.ClassFile;
import org.vrglab.reflections.util.JavassistHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/** scan types, annotations, methods and fields, and stores fqn as key and elements as values */
public class TypeElementsScanner implements Scanner {
    private boolean _includeFields = true;
    private boolean _includeMethods = true;
    private boolean _includeAnnotations = true;
    private boolean _publicOnly = true;
    private Predicate<String> _resultFilter = s -> true; //accept all by default

    public List<Map.Entry<String, String>> scan(ClassFile classFile) {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        String className = classFile.getName();
        if (_resultFilter.test(className) && isPublic(classFile)) {
            entries.add(entry(className, ""));
            if (_includeFields) {
                classFile.getFields().forEach(field -> entries.add(entry(className, field.getName())));
            }
            if (_includeMethods) {
                classFile.getMethods().stream().filter(this::isPublic).
                        forEach(method -> entries.add(entry(className, method.getName() + "(" + String.join(", ", JavassistHelper.getParameters(method)) + ")")));
            }
            if (_includeAnnotations) {
                JavassistHelper.getAnnotations(classFile::getAttribute).stream().filter(_resultFilter).
                        forEach(annotation -> entries.add(entry(className, "@" + annotation)));
            }
        }
        return entries;
    }

    private boolean isPublic(Object object) {
        return !_publicOnly || JavassistHelper.isPublic(object);
    }

    public TypeElementsScanner filterResultsBy(Predicate<String> filter) {
        this._resultFilter = filter;
        return this;
    }

    public TypeElementsScanner includeFields() {
        return includeFields(true);
    }
    public TypeElementsScanner includeFields(boolean include) {
        _includeFields = include; return this;
    }
    public TypeElementsScanner includeMethods() {
        return includeMethods(true);
    }
    public TypeElementsScanner includeMethods(boolean include) {
        _includeMethods = include; return this;
    }
    public TypeElementsScanner includeAnnotations() {
        return includeAnnotations(true);
    }
    public TypeElementsScanner includeAnnotations(boolean include) {
        _includeAnnotations = include; return this;
    }
    public TypeElementsScanner publicOnly(boolean only) {
        _publicOnly = only; return this;
    }
    public TypeElementsScanner publicOnly() {
        return publicOnly(true);
    }
}
