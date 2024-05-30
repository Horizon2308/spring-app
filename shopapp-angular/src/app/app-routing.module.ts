import { NgModule, importProvidersFrom } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { OrderComponent } from './user/components/order/order.component';
import { DetailProductComponent } from './user/components/detail-product/detail-product.component';
import { RegisterComponent } from './user/components/register/register.component';
import { LoginComponent } from './user/components/login/login.component';
import { HomeComponent } from './user/components/home/home.component';
import { OrderConfirmComponent } from './user/components/order-confirm/order-confirm.component';
import { UserProfileComponent } from './user/components/user-profile/user-profile.component';
import { AuthGuardFn } from './guards/auth.guard';
import { HomeAdminComponent } from './admin/components/home-admin/home-admin.component';
import { AdminGuardFn } from './guards/admin.guard';
import { ProductManagermentComponent } from './admin/components/product-managerment/product-managerment.component';
import { AddProductComponent } from './admin/components/add-product/add-product.component';
import { StaffManagermentComponent } from './admin/components/staff-managerment/staff-managerment.component';
import { AddUserComponent } from './admin/components/add-user/add-user.component';
import { PostCounterComponent } from './admin/components/post-counter/post-counter.component';
import { OrderManagermentComponent } from './admin/components/order-managerment/order-managerment.component';
import { EditOrderComponent } from './admin/components/edit-order/edit-order.component';
import { BlogComponent } from './user/components/blog/blog.component';
import { CustomerManagermentComponent } from './admin/components/customer-managerment/customer-managerment.component';
import { CalendarComponent } from './admin/components/calendar/calendar.component';
import { IncomeManagermentComponent } from './admin/components/income-managerment/income-managerment.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'blog', component: BlogComponent },
  { path: 'products/:id', component: DetailProductComponent },
  { path: 'orders', component: OrderComponent, canActivate: [AuthGuardFn] },
  {
    path: 'user-profile',
    component: UserProfileComponent,
    canActivate: [AuthGuardFn],
  },
  { path: 'orders/:id', component: OrderConfirmComponent },
  //  Admin
  {
    path: 'admin',
    component: HomeAdminComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/products',
    component: ProductManagermentComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/products/add',
    component: AddProductComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/staffs',
    component: StaffManagermentComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/staffs/add',
    component: AddUserComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/pos-counter',
    component: PostCounterComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/orders-manager',
    component: OrderManagermentComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/orders-manager/:id',
    component: EditOrderComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/customers',
    component: CustomerManagermentComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/calendar',
    component: CalendarComponent,
    canActivate: [AdminGuardFn],
  },
  {
    path: 'admin/income',
    component: IncomeManagermentComponent,
    canActivate: [AdminGuardFn],
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
