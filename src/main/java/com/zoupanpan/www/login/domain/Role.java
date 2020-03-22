package com.zoupanpan.www.login.domain;

import com.zoupanpan.www.base.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author zoupanpan
 * @version 2020/3/22 15:34
 */
@Entity
@Table(name="role")
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="role_permission",joinColumns={@JoinColumn(name="role_id",referencedColumnName="role_id",foreignKey=@ForeignKey(name="fk_role_permission_role_id"))},
            inverseJoinColumns={@JoinColumn(name="permission_id",referencedColumnName="permission_id",foreignKey=@ForeignKey(name="fk_role_permission_permission_id"))})
    private List<Permission> permissionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
