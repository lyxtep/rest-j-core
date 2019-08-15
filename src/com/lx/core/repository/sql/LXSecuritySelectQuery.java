package com.lx.core.repository.sql;

import com.lx.core.repository.generic.EntityClassWrapper;
import com.lx.core.security.IUserIdentity;


/**
 *
 * @author lx.ds
 */
public class LXSecuritySelectQuery extends LXSelectQuery{
    
    private IUserIdentity _user;

    public LXSecuritySelectQuery(EntityClassWrapper entityClassWrapper, IUserIdentity user) {
        super(entityClassWrapper);
        
        _user = user;
        
        
        /*bool checkUser = (user != null);
        bool isSysAdmin = (checkUser && "sa".Equals(user.GetName(), StringComparison.OrdinalIgnoreCase));
        Parameters = new DynamicParameters();


        // WHERE
        if (checkUser && !isSysAdmin)
        {
            StringBuilder sbCriterion = new StringBuilder();
            int permissionValue;

            // official (no need permissions, all can read)
            sbCriterion.Append("{0}.SecurityClass='").Append(LXSecurityClass.OFFICIAL).Append("'");

            // restricted (no permission -> just my company entities)
            permissionValue = PermissionService.Instance.GetRestrictedReadValue(user, tblEntity.ResourceName);
            sbCriterion.Append(" OR ({0}.SecurityClass='").Append(LXSecurityClass.RESTRICTED).Append("'");
            if (PermissionService.Instance.noPermission(permissionValue))
            {
                // no permission
                sbCriterion.Append(" AND ({0}.Customer=@Customer OR {0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            sbCriterion.Append(")");

            // confidential (no permission -> just my department entities)
            permissionValue = PermissionService.Instance.GetConfidentialReadValue(user, tblEntity.ResourceName);
            sbCriterion.Append(" OR ({0}.SecurityClass='").Append(LXSecurityClass.CONFIDENTIAL).Append("'");
            if (PermissionService.Instance.isDomainAAA(permissionValue))
            {
                // >= AAA permission
                // no filter
            }
            else if (PermissionService.Instance.isDomainAA(permissionValue))
            {
                // >= AA permission
                // no filter
            }
            else if (PermissionService.Instance.isDomainA(permissionValue))
            {
                // >= A permission
                sbCriterion.Append(" AND ({0}.Customer=@Customer OR {0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            else
            {
                // no permission
                sbCriterion.Append(" AND ({0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            sbCriterion.Append(")");

            // secret (no permission -> just my own entities)
            permissionValue = PermissionService.Instance.GetSecretReadValue(user, tblEntity.ResourceName);
            sbCriterion.Append(" OR ({0}.SecurityClass='").Append(LXSecurityClass.SECRET).Append("'");
            if (PermissionService.Instance.isDomainAAA(permissionValue))
            {
                // >= AAA permission
                // no filter
            }
            else if (PermissionService.Instance.isDomainAA(permissionValue))
            {
                // >= AA permission
                sbCriterion.Append(" AND ({0}.Customer=@Customer OR {0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            else if (PermissionService.Instance.isDomainA(permissionValue))
            {
                // >= A permission
                sbCriterion.Append(" AND ({0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            else
            {
                // no permission
                sbCriterion.Append(" AND {0}.Owner=@Owner");
            }
            sbCriterion.Append(")");

            // topsecret (no permission -> just sa can read entities)
            permissionValue = PermissionService.Instance.GetTopSecretReadValue(user, tblEntity.ResourceName);
            sbCriterion.Append(" OR ({0}.SecurityClass='").Append(LXSecurityClass.TOPSECRET).Append("'");
            if (PermissionService.Instance.isDomainAAA(permissionValue))
            {
                // >= AAA permission
                sbCriterion.Append(" AND ({0}.Customer=@Customer OR {0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            else if (PermissionService.Instance.isDomainAA(permissionValue))
            {
                // >= AA permission
                sbCriterion.Append(" AND ({0}.Department=@Department OR {0}.Owner=@Owner)");
            }
            else if (PermissionService.Instance.isDomainA(permissionValue))
            {
                // >= A permission
                sbCriterion.Append(" AND {0}.Owner=@Owner");
            }
            else
            {
                // no permission
                sbCriterion.Append(" AND {0}.Owner=@Owner AND {0}.Owner='sa'");
            }
            sbCriterion.Append(")");

            if (sbCriterion.Length > 0)
            {
                addAndCriterion(" (" + String.Format(sbCriterion.ToString(), LXSelectQuery<TEntity>.ELEMENT_ALIAS) + ")");
                Parameters.Add("Customer", user.GetCustomer());
                Parameters.Add("Department", user.GetDepartment());
                Parameters.Add("Owner", user.GetName());
            }
        }*/
    }
}
