import {Injectable} from "@angular/core";
import {AuthService} from "tcomad-oidc";
import {from, Observable} from "rxjs";
import {constants} from "../../constants/constants";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private authService = new AuthService(environment.keycloak, constants.client);

    public isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }

    public isAdmin(): boolean {
        return constants.adminRoles.some(role => this.authService.hasRole(role));
    }

    public getToken(): Observable<string> {
        return from(this.authService.getToken());
    }

    public logout(): void {
        this.authService.logout();
    }
}