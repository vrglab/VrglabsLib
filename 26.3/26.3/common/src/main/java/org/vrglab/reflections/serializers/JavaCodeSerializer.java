package org.vrglab.reflections.serializers;

import org.vrglab.reflections.Reflections;
import org.vrglab.reflections.scanners.TypeElementsScanner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** source code serialization for {@link Reflections} <pre>{@code reflections.save(file, new JavaCodeSerializer())}</pre>
 * <p></p>an example of produced java source:
 * <pre>{@code
 * public interface MyTestModelStore {
 *   interface org {
 *     interface reflections {
 *       interface TestModel$C4 {
 *         interface fields {
 *           interface f1 {}
 *           interface f2 {}
 *         }
 *         interface methods {
 *           interface m1 {}
 *           interface add {}
 *         }
 *         interface annotations {
 *           ...
 *         }
 *       }
 *     }
 *   }
 * }
 * }</pre>
 * <p>this allows strongly typed access by fqn to type elements - packages, classes, annotations, fields and methods:
 * <pre>{@code MyTestModelStore.org.Vrglab.Reflections.TestModel$C1.methods.m1.class}</pre>
 * <p>depends on {@link org.vrglab.reflections.scanners.TypeElementsScanner} configured
 */
public class JavaCodeSerializer implements Serializer {

    private static final String PATH_SEPARATOR = "_";
    private static final String DOUBLE_SEPARATOR = "__";
    private static final String DOT_SEPARATOR = ".";
    private static final String ARRAY_DESCRIPTOR = "$$";
    private static final String TOKEN_SEPARATOR = "_";

    private StringBuilder _sb;
    private List<String> _prevPaths;
    private int _indent;

    public Reflections read(InputStream inputStream) {
        throw new UnsupportedOperationException("read is not implemented on JavaCodeSerializer");
    }

    /**
     * serialize and save to java source code
     * @param name should be in the pattern {@code path/path/path/package.package.classname},
     */
    public File save(Reflections reflections, String name) {
        if (name.endsWith("/")) {
            name = name.substring(0, name.length() - 1); //trim / at the end
        }

        //prepare file
        String filename = name.replace('.', '/').concat(".java");
        File file = Serializer.prepareFile(filename);

        //get package and class names
        String packageName;
        String className;
        int lastDot = name.lastIndexOf('.');
        if (lastDot == -1) {
            packageName = "";
            className = name.substring(name.lastIndexOf('/') + 1);
        } else {
            packageName = name.substring(name.lastIndexOf('/') + 1, lastDot);
            className = name.substring(lastDot + 1);
        }

        //generate
        try {
            _sb = new StringBuilder();
            _sb.append("//generated using Reflections JavaCodeSerializer").append(" [").append(new Date()).append("]").append("\n");
            if (packageName.length() != 0) {
                _sb.append("package ").append(packageName).append(";\n");
                _sb.append("\n");
            }
            _sb.append("public interface ").append(className).append(" {\n\n");
            toString(reflections);
            _sb.append("}\n");

            Files.write(new File(filename).toPath(), _sb.toString().getBytes(Charset.defaultCharset()));

        } catch (IOException e) {
            throw new RuntimeException();
        }

        return file;
    }

    private void toString(Reflections reflections) {
        Map<String, Set<String>> map = reflections.getStore().get(TypeElementsScanner.class.getSimpleName());
        _prevPaths = new ArrayList<>();
        _indent = 1;

        map.keySet().stream().sorted().forEach(fqn -> {
            List<String> typePaths = Arrays.asList(fqn.split("\\."));
            String className = fqn.substring(fqn.lastIndexOf('.') + 1);
            List<String> fields = new ArrayList<>();
            List<String> methods = new ArrayList<>();
            List<String> annotations = new ArrayList<>();
            map.get(fqn).stream().sorted().forEach(element -> {
                if (element.startsWith("@")) {
                    annotations.add(element.substring(1));
                } else if (element.contains("(")) {
                    if (!element.startsWith("<")) {
                        int i = element.indexOf('(');
                        String name = element.substring(0, i);
                        String params = element.substring(i + 1, element.indexOf(")"));
                        String paramsDescriptor = params.length() != 0 ? TOKEN_SEPARATOR + params.replace(DOT_SEPARATOR, TOKEN_SEPARATOR).replace(", ", DOUBLE_SEPARATOR).replace("[]", ARRAY_DESCRIPTOR) : "";
                        methods.add(!methods.contains(name) ? name : name + paramsDescriptor);
                    }
                } else if (!element.isEmpty()) {
                    fields.add(element);
                }
            });

            int i = indentOpen(typePaths, _prevPaths);
            addPackages(typePaths, i);
            addClass(typePaths, className);
            addFields(typePaths, fields);
            addMethods(typePaths, fields, methods);
            addAnnotations(typePaths, annotations);

            _prevPaths = typePaths;
        });

        indentClose(_prevPaths);
    }

    protected int indentOpen(List<String> typePaths, List<String> prevPaths) {
        int i = 0;
        while (i < Math.min(typePaths.size(), prevPaths.size()) && typePaths.get(i).equals(prevPaths.get(i))) {
            i++;
        }
        for (int j = prevPaths.size(); j > i; j--) {
            _sb.append(indent(--_indent)).append("}\n");
        }
        return i;
    }

    protected void indentClose(List<String> prevPaths) {
        for (int j = prevPaths.size(); j >= 1; j--) {
            _sb.append(indent(j)).append("}\n");
        }
    }

    protected void addPackages(List<String> typePaths, int i) {
        for (int j = i; j < typePaths.size() - 1; j++) {
            _sb.append(indent(_indent++)).append("interface ").append(uniqueName(typePaths.get(j), typePaths, j)).append(" {\n");
        }
    }

    protected void addClass(List<String> typePaths, String className) {
        _sb.append(indent(_indent++)).append("interface ").append(uniqueName(className, typePaths, typePaths.size() - 1)).append(" {\n");
    }

    protected void addFields(List<String> typePaths, List<String> fields) {
        if (!fields.isEmpty()) {
            _sb.append(indent(_indent++)).append("interface fields {\n");
            for (String field : fields) {
                _sb.append(indent(_indent)).append("interface ").append(uniqueName(field, typePaths)).append(" {}\n");
            }
            _sb.append(indent(--_indent)).append("}\n");
        }
    }

    protected void addMethods(List<String> typePaths, List<String> fields, List<String> methods) {
        if (!methods.isEmpty()) {
            _sb.append(indent(_indent++)).append("interface methods {\n");
            for (String method : methods) {
                String methodName = uniqueName(method, fields);
                _sb.append(indent(_indent)).append("interface ").append(uniqueName(methodName, typePaths)).append(" {}\n");
            }
            _sb.append(indent(--_indent)).append("}\n");
        }
    }

    protected void addAnnotations(List<String> typePaths, List<String> annotations) {
        if (!annotations.isEmpty()) {
            _sb.append(indent(_indent++)).append("interface annotations {\n");
            for (String annotation : annotations) {
                _sb.append(indent(_indent)).append("interface ").append(uniqueName(annotation, typePaths)).append(" {}\n");
            }
            _sb.append(indent(--_indent)).append("}\n");
        }
    }

    private String uniqueName(String candidate, List<String> prev, int offset) {
        String normalized = normalize(candidate);
        for (int i = 0; i < offset; i++) {
            if (normalized.equals(prev.get(i))) {
                return uniqueName(normalized + TOKEN_SEPARATOR, prev, offset);
            }
        }
        return normalized;
    }

    private String normalize(String candidate) {
        return candidate.replace(DOT_SEPARATOR, PATH_SEPARATOR);
    }

    private String uniqueName(String candidate, List<String> prev) {
        return uniqueName(candidate, prev, prev.size());
    }

    private String indent(int times) {
        return IntStream.range(0, times).mapToObj(i -> "  ").collect(Collectors.joining());
    }
}
