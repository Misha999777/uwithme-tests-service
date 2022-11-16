import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../service/user.service";

@Component({
    selector: 'testsystem-complete-auth',
    templateUrl: './complete-auth.component.html'
})
export class CompleteAuthComponent implements OnInit {

    constructor(private router: Router,
                private route: ActivatedRoute,
                private userService: UserService) {}

    ngOnInit(): void {
        if (this.userService.isLoggedIn()) {
            this.router.navigate([""], {queryParams: this.route.snapshot.queryParams})
                .then(() => console.log("Navigated user"))
        }
    }

}