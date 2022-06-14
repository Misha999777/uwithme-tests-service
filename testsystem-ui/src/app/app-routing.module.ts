import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {EditTestComponent} from "./page/edit-test/edit-test.component";
import {EditQuestionComponent} from "./page/edit-question/edit-question.component";
import {StartComponent} from "./page/start-test/start.component";
import {TestSessionComponent} from "./page/test-session/test-session.component";
import {GuardService} from "./service/guard.service";
import {CompleteAuthComponent} from "./page/complete-auth/complete-auth.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'test',
    pathMatch: 'full',
  },
  {
    path: 'test',
    component: EditTestComponent,
    canActivate: [GuardService]
  },
  {
    path: 'question',
    component: EditQuestionComponent,
    canActivate: [GuardService]
  },
  {
    path: 'start',
    component: StartComponent,
    canActivate: [GuardService]
  },
  {
    path: 'start/finished',
    component: StartComponent,
    canActivate: [GuardService]
  },
  {
    path: 'session',
    component: TestSessionComponent,
    canActivate: [GuardService]
  },
  {
    path: 'auth',
    component: CompleteAuthComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
