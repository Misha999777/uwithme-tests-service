import {Injectable} from "@angular/core";
import {CanActivate} from "@angular/router";
import {AuthServiceFactory} from "../service/auth.service.factory";
import {AuthService} from "tcomad-oidc";

@Injectable({
    providedIn: 'root'
})
export class UserUtility {

    authService: AuthService;

    constructor(authServiceFactory: AuthServiceFactory) {
        this.authService = authServiceFactory.getAuthService();
    }

    public isAdmin(): boolean {
        return this.authService.hasRole("ROLE_ADMIN")
    }
}