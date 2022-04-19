import {Injectable} from "@angular/core";
import { AuthService } from "tcomad-oidc";

@Injectable({
    providedIn: 'root'
})
export class AuthServiceFactory {

    private authService = new AuthService("http://localhost:8080/auth/realms/test",
        "TestSystem-UI",
        "http://localhost:4200",
        true);

    getAuthService(): AuthService {
        return this.authService;
    }
}