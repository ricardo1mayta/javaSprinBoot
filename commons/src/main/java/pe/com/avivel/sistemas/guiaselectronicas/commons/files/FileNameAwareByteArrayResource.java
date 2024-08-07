package pe.com.avivel.sistemas.guiaselectronicas.commons.files;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.lang.NonNull;

public class FileNameAwareByteArrayResource extends ByteArrayResource {

    private final String fileName;

    public FileNameAwareByteArrayResource(@NonNull String fileName, byte[] byteArray) {
        super(byteArray, "");
        this.fileName = fileName;
    }

    @SuppressWarnings("unused")
    public FileNameAwareByteArrayResource(@NonNull String fileName, byte[] byteArray, String description) {
        super(byteArray, description);
        this.fileName = fileName;
    }

    @Override
    @NonNull
    public String getFilename() {
        return fileName;
    }
}
