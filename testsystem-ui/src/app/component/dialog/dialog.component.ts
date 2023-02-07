import {Component, Inject} from "@angular/core";
import {UserService} from "../../service/user.service";
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
    selector: 'testsystem-dialog',
    templateUrl: 'dialog.component.html',
})
export class DialogComponent {

    constructor(@Inject(MAT_DIALOG_DATA) public text: string,
                private userService: UserService) {
    }

    onExitClicked() {
        this.userService.logout();
    }
}