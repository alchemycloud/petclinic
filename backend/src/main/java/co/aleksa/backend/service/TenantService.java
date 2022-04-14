package co.aleksa.backend.service;

import java.sql.SQLException;
import java.util.List;

public interface TenantService {

    List<String> getTenants() throws SQLException;

    List<String> getTenantsByEmail(String email) throws SQLException;

    List<String> getTenantsBySetPasswordCode(String code) throws SQLException;
}
