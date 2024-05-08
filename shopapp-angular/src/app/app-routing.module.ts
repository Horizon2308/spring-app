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

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
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
];
@NgModule({
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
