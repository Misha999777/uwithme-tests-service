import {Injectable} from "@angular/core";
import {AuthService} from "tcomad-oidc";
import {from, Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private authService = new AuthService("http://localhost:8080/auth/realms/test",
        "TestSystem-UI",
        true);

    public isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }

    public isAdmin(): boolean {
        return this.authService.hasRole("ROLE_ADMIN")
    }

    public getToken(): Observable<string> {
        return from(this.authService.getToken());
    }

    public logout(): void {
        this.authService.logout();
    }
}