import {Injectable} from "@angular/core";
import {AuthService} from "tcomad-oidc";
import {from, Observable} from "rxjs";
import {constants} from "../../constants/constants";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private authService = new AuthService(constants.keycloakUrl, constants.client, true);

    public isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }

    public isAdmin(): boolean {
        return this.authService.getRoles()
            .some(role => constants.adminRoles.includes(role));
    }

    public getToken(): Observable<string> {
        return from(this.authService.getToken());
    }

    public logout(): void {
        this.authService.logout();
    }
}